package ru.idaspin.helperforbulgakov.activities_old.budget;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;
import ru.idaspin.helperforbulgakov.widgets.BaseRecyclerAdapter;

/**
 * Created by User.
 * Date: 16.07.2017
 * Time: 1:27 PM
 */

public class NotesAdapter extends BaseRecyclerAdapter<NotesHolder, Note> {

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotesHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.old_item_note, parent, false));
    }
    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(getItem(position));
        holder.setId(position);
    }
}
