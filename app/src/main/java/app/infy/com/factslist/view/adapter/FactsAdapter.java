package app.infy.com.factslist.view.adapter;

import android.content.Context;
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

    private List<Rows> mFactsList;
    Context mContext;

    public FactsAdapter(Context context) {
        this.mFactsList = Collections.emptyList();
        this.mContext = context;
    }

    /*Inflating the views with and applying DataBinding concept with the views*/
    @Override
    public FactsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FactsRowItemsBinding itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.facts_row_items, parent, false);
        return new FactsAdapterViewHolder(itemsBinding);
    }

    @Override
    public void onBindViewHolder(FactsAdapterViewHolder holder, int position) {
        holder.bindFacts(mFactsList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return mFactsList.size();
    }

    /*method to change the data in list and update the RecyclerView*/
    public void setmFactsList(List<Rows> mFactsList) {
        this.mFactsList = mFactsList;
        notifyDataSetChanged();
    }

    /*A view holder class which will have the reference of all views used in the Row*/
    public static class FactsAdapterViewHolder extends RecyclerView.ViewHolder {
        FactsRowItemsBinding mItemBinding;

        /*constructor for the class*/
        public FactsAdapterViewHolder(FactsRowItemsBinding mItemBinding) {
            super(mItemBinding.itemFacts);
            this.mItemBinding = mItemBinding;
        }

        /*method to change the data in the row by reusing the existing row on scroll*/
        void bindFacts(Rows rows, Context context) {
            /*reusing the view and changing the contents base don the position it will optimize the scrolling */
            if (mItemBinding.getFactsViewModel() == null) {
                mItemBinding.setFactsViewModel(new FactsListItemViewModel(rows, context));
            } else {
                mItemBinding.getFactsViewModel().setRow(rows);
            }
        }
    }
}
