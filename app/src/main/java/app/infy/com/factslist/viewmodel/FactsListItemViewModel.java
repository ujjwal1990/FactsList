package app.infy.com.factslist.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import app.infy.com.factslist.model.Rows;

public class FactsListItemViewModel extends BaseObservable {

    private Rows rows;
    private Context context;

    public FactsListItemViewModel(Rows rows, Context context) {
        this.rows = rows;
        this.context = context;
    }


    public String getFactThumb() {
        return rows.getImageHref();
    }

    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }


    public String getTitle() {
        return rows.getTitle();
    }

    public String getDescription() {
        return rows.getDescription();
    }

    public String getImage() {
        return rows.getImageHref();
    }

    //
    public void setRow(Rows row) {
        this.rows = row;
        notifyChange();
    }
}