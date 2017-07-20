package ru.idaspin.helperforbulgakov.activities_old.budget;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;
import ru.idaspin.helperforbulgakov.activities_old.budget.budgetTools.Budget;
import ru.idaspin.helperforbulgakov.activities_old.budget.budgetTools.BudgetActivityTools;
import ru.idaspin.helperforbulgakov.activities_old.menu.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;
import ru.idaspin.helperforbulgakov.activities_old.DateUtil;
import ru.idaspin.helperforbulgakov.widgets.EmptyRecyclerView;

public class BudgetActivity extends BaseMenuActivity {

    public Budget budget;

    private TextView budgetTextView;
    private EditText budgetEditText;

    private LinearLayout noteAddLayout;
    private EditText noteTopEditText;
    private EditText noteCostEditText;
    private EditText noteTextEditText;

    private EmptyRecyclerView recyclerView;
    public NotesAdapter notesAdapter;

    private static int countOfNotes = 0;

    private boolean addNewNote = false;

    @Override
    protected int setActivityTitle() {
        return R.string.title_budget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.old_activity_budget);
        BudgetActivityTools.setBudgetActivity(this);

        budget = new Budget(getApplicationContext());

        recyclerView = (EmptyRecyclerView)findViewById(R.id.list_notes_Recycle_layout);

        noteAddLayout = (LinearLayout) findViewById(R.id.add_note_layout);

        budgetTextView = (TextView) findViewById(R.id.budget_text_view);
        budgetEditText = (EditText) findViewById(R.id.budget_edit_text);

        noteTopEditText = (EditText) findViewById(R.id.note_top_edit_text);
        noteCostEditText = (EditText) findViewById(R.id.note_cost_edit_text);
        noteTextEditText = (EditText) findViewById(R.id.note_text_edit_text);

        budgetTextView.setText("" + budget.getBudget() + Budget.RUB_TEXT);

        createButton();
        notesAdapter = new NotesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(findViewById(R.id.notes_frame).getContext()));
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.attachToRecyclerView(recyclerView);
        downloadFromDB();

        setupFloatingActionButton(v -> {
            if(!addNewNote) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                noteAddLayout.setLayoutParams(layoutParams);
                noteAddLayout.setOrientation(LinearLayout.VERTICAL);
                addNewNote = true;
            }
        }, R.mipmap.ic_add_button);

        countOfNotes = notesAdapter.getItemCount();
    }

    public void downloadFromDB(){
            RepositoryProvider.provideRisensRepository()
                    .getNote(0)
                    .subscribe((notes) -> {
                        notesAdapter.changeDataSet(notes);
                        // здесь notes - это List<Note>
                    }, (throwable -> {
                        // throwable можно проигнорить, но не удалять. Иначе, если ошибка всё таки появится, вылетит эксепшн
                    }));
    }

    private void createButton(){
        ImageButton plusButton = (ImageButton) findViewById(R.id.plus_budget_image_button);
        ImageButton minusButton = (ImageButton)findViewById(R.id.minus_budget_image_button);
        ImageButton agreeButton = (ImageButton) findViewById(R.id.note_agree_image_button);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.note_cancel_image_button);

        plusButton.setOnClickListener(v -> {
            if(budgetEditText.getText().toString().length() == 0){
                showToastMessage("Введите сумму!");
            }
            else {
                budget.plusBudget(Float.parseFloat(budgetEditText.getText().toString()));
                showToastMessage("Бюджет увеличен на " + budgetEditText.getText().toString()
                        + Budget.RUB_TEXT);
                updateBudget();
            }
        });
        minusButton.setOnClickListener(v -> {
            if(budgetEditText.getText().toString().length() == 0){
                showToastMessage("Введите сумму!");
            }
            else if(budget.minusBudget(Float.parseFloat(budgetEditText.getText().toString()))){
                showToastMessage("Бюджет уменьшен на " + budgetEditText.getText().toString()
                        + Budget.RUB_TEXT);
            }
            else {
                showToastMessage("Недостаточно средств!");
            }
            updateBudget();
        });
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if((noteTopEditText.length() == 0) || (noteCostEditText.length() == 0) || (noteTextEditText.length() == 0)){
                    showToastMessage("Заполните все поля!");
                }
                else if(budget.minusBudget(Float.parseFloat(noteCostEditText.getText().toString()))){
                    String noteCost = String.valueOf(Budget.rintBudget(Float.parseFloat(noteCostEditText.getText().toString())));

                    Note newNote = new Note(
                            countOfNotes, noteTopEditText.getText().toString(), DateUtil.getCurrentData(),
                            noteTextEditText.getText().toString(), noteCost);
                    /***Эту строчку удалить*******/
                    notesAdapter.add(newNote);/***/
                    /*****************************/
                    addNoteDB(newNote);
                    countOfNotes++;

                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT, 0);
                    noteAddLayout.setLayoutParams(layoutParams);
                    addNewNote = false;

                    noteTopEditText.setText("");
                    noteCostEditText.setText("");
                    noteTextEditText.setText("");

                    updateBudget();

                    showToastMessage("Заметка добавлена!");
                }
                else {
                    showToastMessage("Недостаточно средств!");
                }
            }
        });
        cancelButton.setOnClickListener(v -> {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, 0);
            noteAddLayout.setLayoutParams(layoutParams);
            addNewNote = false;

            noteTopEditText.setText("");
            noteCostEditText.setText("");
            noteTextEditText.setText("");
        });
    }

    public void addNoteDB(Note note){
        RepositoryProvider.provideRisensRepository().addNote(
                note.getTop(), note.getText(), note.getDate(), note.getBudgetFor())
                .subscribe((response) -> {downloadFromDB();}, (throwable) -> {/*do nothing*/});;
    }

    public void updateBudget(){
        budgetTextView.setText("" + budget.getBudget() + Budget.RUB_TEXT);
        budgetEditText.setText("");
    }

}
