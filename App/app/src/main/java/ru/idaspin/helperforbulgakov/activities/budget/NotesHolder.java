package ru.idaspin.helperforbulgakov.activities.budget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;

/**
 * Created by User.
 * Date: 16.07.2017
 * Time: 9:22 PM
 *
 * Updated by idaspin.
 * Date: 7/19/2017
 * Time: 4:23 AM
 * + Re:design
 * + Attached poster
 */

class NotesHolder extends RecyclerView.ViewHolder {

    @BindString(R.string.budget_val_caps) String val;

    @BindView(R.id.budget_item_cost)    TextView mTextViewCost;
    @BindView(R.id.budget_item_date)    TextView mTextViewDate;
    @BindView(R.id.budget_item_title)   TextView mTextViewTitle;
    @BindView(R.id.budget_item_message) TextView mTextViewMessage;

    NotesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    
    public void bind(@NonNull Note note) {
        mTextViewMessage.setText(note.getText());
        mTextViewTitle.setText(note.getTop());
        mTextViewDate.setText(note.getDate());
        mTextViewCost.setText(String.format("%s %s", note.getBudgetFor(), val));
    }
    
}
