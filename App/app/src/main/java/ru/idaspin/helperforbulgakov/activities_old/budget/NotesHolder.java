package ru.idaspin.helperforbulgakov.activities_old.budget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;
import ru.idaspin.helperforbulgakov.activities_old.budget.budgetTools.BudgetActivityTools;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

/**
 * Created by User.
 * Date: 16.07.2017
 * Time: 1:27 PM
 */

public class NotesHolder extends RecyclerView.ViewHolder {

    private final int closeSize = 40;
    public boolean isClosable = false;
    private String closeText = "";
    private String allText = "";

    private int id;

    @BindView(R.id.note_frame)LinearLayout linearLayout;
    @BindView(R.id.budget_top_note_text_view)TextView topTextView;
    @BindView(R.id.date_budget_note_text_view)TextView dateTextView;
    @BindView(R.id.text_note_budget_note_text_view)TextView noteTextView;
    @BindView(R.id.note_cost)TextView costTextView;
    @BindView(R.id.note_did_image_button)ImageButton didImageButton;
    @BindView(R.id.note_didnt_image_button)ImageButton didntImageButton;
    @BindView(R.id.note_did_linear_layout)LinearLayout noteDidLinearLayout;

    public NotesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(@NonNull Note note) {
        topTextView.setText(note.getTop());
        dateTextView.setText(note.getDate());
        costTextView.setText(note.getBudgetFor() + " РУБ.");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        noteDidLinearLayout.setLayoutParams(layoutParams);
        noteDidLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        if (note.getText().length() > closeSize) {
            closeText = getCloseText(note.getText());
            allText = note.getText();
            noteTextView.setText(closeText);
            isClosable = true;
        } else {
            noteTextView.setText(note.getText());
        }


        linearLayout.setOnClickListener(new View.OnClickListener() {

            private boolean isOpen = false;

            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    if(isClosable) {
                        noteTextView.setText(allText);
                    }

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    noteDidLinearLayout.setLayoutParams(layoutParams);
                    noteDidLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    isOpen = true;
                } else {

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 0);
                    noteDidLinearLayout.setLayoutParams(layoutParams);
                    noteDidLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    if(isClosable) {
                        noteTextView.setText(closeText);
                    }
                    isOpen = false;

                }
            }
        });

        didImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Удалить эту строчку кода*/
                BudgetActivityTools.getInstance().notesAdapter.removeItem(id);
                /***/

                RepositoryProvider.provideRisensRepository().removeNote(id);
                BudgetActivityTools.getInstance().downloadFromDB();
                BudgetActivityTools.getInstance().showToastMessage("Заметка была удалена. Цель достигнута. Поздравляю!");
            }
        });
        didntImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetActivityTools.getInstance().budget.plusBudget(
                        Float.parseFloat(BudgetActivityTools.getInstance().notesAdapter.getItem(id).getBudgetFor()));

                BudgetActivityTools.getInstance().notesAdapter.removeItem(id);
                BudgetActivityTools.getInstance().updateBudget();
                BudgetActivityTools.getInstance().showToastMessage("Заметка была удалена. Цель недостигнута. Возвращаю деньги.");
            }
        });
    }

    private String getCloseText(String string){
        String result = "";
        for(int i = 0; (i < closeSize) && (i < string.length()); i++){
            result += string.charAt(i);
        }
        if(result.length() < string.length()){
            result += "...";
        }
        return result;
    }

    public void setId(int id){
        this.id = id;
    }


}
