package com.mg.kode.kodebrowser.ui.home.favorites;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.mData.model.Favorites;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;
import com.mg.kode.kodebrowser.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private static final int ADD_NEW_VIEW_TYPE = 1;
    private static final int FAVORITE_VIEW_TYPE = 2;

    private List<Favorites> mData;
    private ItemClickedListener mClickListener;
    private View.OnClickListener mAddBtnListener;
    private LongClickListener mLongClickListener;

    public interface LongClickListener {
        void onLongItemClicked(View v, Favorites item);
    }

    FavoritesAdapter() {
    	this(null);
    }

    FavoritesAdapter(List<Favorites> favorites) {
        mData = favorites != null ? favorites : new ArrayList<>(0);
    }

    FavoritesAdapter(List<Favorites> favorites, ItemClickedListener listener) {
        this(favorites);
        setItemClickListener(listener);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        if (getItemViewType(position) == ADD_NEW_VIEW_TYPE) {
            Picasso.get()
                    .load(R.drawable.add_button)
                    .into(holder.ivFavoriteIcon);
            holder.tvFavoriteLabel.setText(StringUtils.getString(R.string.add));
        } else {
            Favorites favorites = getItem(position);
            Picasso.get()
                    .load(favorites.getWebsite().getFavicon())
                    .error(R.drawable.ic_favorite_default)
                    .fit()
                    .centerCrop()
                    .into(holder.ivFavoriteIcon);
            holder.tvFavoriteLabel.setText(favorites.getTitle());
        }
    }

    void setItemClickListener(ItemClickedListener listener) {
        mClickListener = listener;
    }

    void setLongItemClickListener(LongClickListener listener) {
        mLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    /**
     * Returns an item for provided position.
     *
     * @param position position of an item in the list.
     * @return {@link Favorites} object from the list.
     */
    Favorites getItem(int position) {
        return mData.get(position);
    }

    /**
     * Sets adapter's new mData source.
     *
     * @param mData list of {@link Favorites} items.
     */
    public void setData(List<Favorites> mData) {
        if (mData != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(mData, mData));
            mData.clear();
            mData.addAll(mData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    /**
     * Add new favorite item to adapter's mData source.
     *
     * @param mData {@link Favorites} item.
     */
    void addData(Favorites mData) {
        if (mData != null) {
            mData.add(mData);
            notifyItemInserted(mData.size() - 1);
        }
    }

    /**
     * Remove favorite item from adapter's mData source.
     *
     * @param mData {@link Favorites} item to be removed.
     */
    void removeData(Favorites mData) {
        if (mData != null) {
            int index = mData.indexOf(mData);
            if (index != -1) {
                mData.remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    void setOnAddBtnClickListener(View.OnClickListener listener) {
        mAddBtnListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemCount() - 1 == position ? ADD_NEW_VIEW_TYPE : FAVORITE_VIEW_TYPE;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_site_item, parent, false);
        return new FavoritesViewHolder(root);
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvFavoriteLabel;
        private ImageView ivFavoriteIcon;

        FavoritesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.tvFavoriteLabel = itemView.findViewById(R.id.tv_favorite_site_label);
            this.ivFavoriteIcon = itemView.findViewById(R.id.iv_favorite_site_icon);
        }

        @Override
        public void onClick(View v) {
            if (this.getAdapterPosition() == getItemCount() - 1) {
                // add was clicked
                if (mAddBtnListener != null) {
                    mAddBtnListener.onClick(v);
                }
            } else if (mClickListener != null) {
                mClickListener.onItemClicked(getItem(this.getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (this.getAdapterPosition() != getItemCount() - 1) {
                if (mLongClickListener != null) {
                    mLongClickListener.onLongItemClicked(v, mData.get(getAdapterPosition()));
                }
                return true;
            }
            return false;
        }
    }

    private class DiffCallback extends DiffUtil.Callback {

        private final List<Favorites> oldData;
        private final List<Favorites> newData;

        public DiffCallback(List<Favorites> oldData, List<Favorites> newData) {
            this.oldData = oldData;
            this.newData = newData;
        }

        @Override
        public int getOldListSize() {
            return this.oldData.size();
        }

        @Override
        public int getNewListSize() {
            return this.newData.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return this.oldData.get(oldItemPosition).equals(this.newData.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Favorites oldF = this.oldData.get(oldItemPosition);
            Favorites newF = this.newData.get(newItemPosition);
            return oldF.getTitle().equals(newF.getTitle()) &&
                    oldF.getWebsite().getFavicon().equals(newF.getWebsite().getFavicon());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            // Implement method if you're going to use ItemAnimator
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
