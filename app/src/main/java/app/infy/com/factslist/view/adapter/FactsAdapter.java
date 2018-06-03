package app.infy.com.factslist.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import app.infy.com.factslist.R;
import app.infy.com.factslist.databinding.FactsRowItemsBinding;
import app.infy.com.factslist.model.Rows;
import app.infy.com.factslist.viewmodel.FactsListItemViewModel;

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactsAdapterViewHolder> {

    private List<Rows> factsList;

    public FactsAdapter() {
        this.factsList = Collections.emptyList();
    }

    @Override
    public FactsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FactsRowItemsBinding itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.facts_row_items, parent, false);
        return new FactsAdapterViewHolder(itemsBinding);
    }

    @Override
    public void onBindViewHolder(FactsAdapterViewHolder holder, int position) {
        holder.bindFacts(factsList.get(position));
    }

    @Override
    public int getItemCount() {
        return factsList.size();
    }

    public void setFactsList(List<Rows> factsList) {
        this.factsList = factsList;
        notifyDataSetChanged();
    }

    public static class FactsAdapterViewHolder extends RecyclerView.ViewHolder {
        FactsRowItemsBinding mItemBinding;
        public FactsAdapterViewHolder(FactsRowItemsBinding mItemBinding) {
            super(mItemBinding.itemFacts);
            this.mItemBinding = mItemBinding;
        }

        void bindFacts(Rows rows) {
            if (mItemBinding.getFactsViewModel() == null) {
                mItemBinding.setFactsViewModel(new FactsListItemViewModel(rows, itemView.getContext()));
            } else {
                mItemBinding.getFactsViewModel().setRow(rows);
            }
        }
    }
}
