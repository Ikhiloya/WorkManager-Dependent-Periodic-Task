package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> people;
    private ListItemClickListener listItemClickListener;

    public PersonAdapter(List<Person> people, ListItemClickListener listItemClickListener) {
        this.people = people;
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * an interface to handle click events on a card
     */
    public interface ListItemClickListener {
        void onListItemClick(Person person, int adapterPosition);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.person_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(people.get(position), this.listItemClickListener);
    }

    @Override
    public int getItemCount() {
        return people != null ? people.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView staffId;
        private ImageView stateIcon;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            staffId = itemView.findViewById(R.id.personId);
            stateIcon = itemView.findViewById(R.id.stateIcon);
        }


        @SuppressLint("SetTextI18n")
        void bind(Person person, ListItemClickListener listItemClickListener) {
            fullName.setText(person.getFirstName().trim() + " " + person.getLastName().trim());
            String id = person.getId() == null ? "--" : String.valueOf(person.getId());
            staffId.setText(id);

            if (person.getState() != null) {
                if (person.getState().equalsIgnoreCase(Constant.PENDING) || person.getState().equalsIgnoreCase(Constant.UPDATING)) {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_pending_24dp);
                } else {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_active_24dp);
                }
                stateIcon.setVisibility(View.VISIBLE);
            } else {
                stateIcon.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> listItemClickListener.onListItemClick(person, getAdapterPosition()));
        }
    }
}

