package com.example.baseproject.imagebottompicker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baseproject.R;
import com.example.baseproject.imagebottompicker.ImageBottomPicker;
import com.example.baseproject.imagebottompicker.view.SquareFrameLayout;
import com.example.baseproject.imagebottompicker.view.SquareImageView;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {


    ArrayList<PickerTile> pickerTiles;
    Context context;
    ImageBottomPicker.Builder builder;
    OnItemClickListener onItemClickListener;
    ArrayList<Uri> selectedUriList;

    @SuppressLint("Range")
    public GalleryAdapter(Context context, ImageBottomPicker.Builder builder) {

        this.context = context;
        this.builder = builder;

        pickerTiles = new ArrayList<>();
        selectedUriList = new ArrayList<>();

        if (builder.showCamera) {
            pickerTiles.add(new PickerTile(PickerTile.CAMERA));
        }

        if (builder.showGallery) {
            pickerTiles.add(new PickerTile(PickerTile.GALLERY));
        }

        Cursor cursor = null;
        try {
            String[] columns;
            String orderBy;
            Uri uri;
            if (builder.mediaType == ImageBottomPicker.Builder.MediaType.IMAGE) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                columns = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.WIDTH};
                orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";
            } else {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                columns = new String[]{MediaStore.Video.VideoColumns.DATA};
                orderBy = MediaStore.Video.VideoColumns.DATE_ADDED + " DESC";
            }


            cursor = context.getApplicationContext().getContentResolver().query(uri, columns, null, null, orderBy);
            //imageCursor = sContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);


            if (cursor != null) {

                int count = 0;
                while (cursor.moveToNext() && count < builder.previewMaxCount) {

                    String dataIndex;
                    if (builder.mediaType == ImageBottomPicker.Builder.MediaType.IMAGE) {
                        dataIndex = MediaStore.Images.Media.DATA;

                        
                        String imageLocation = cursor.getString(cursor.getColumnIndex(dataIndex));
                        int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                        int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));

                        File imageFile = new File(imageLocation);
                        pickerTiles.add(new PickerTile(Uri.fromFile(imageFile), height, width));
                        count++;

                    } else {
                        dataIndex = MediaStore.Video.VideoColumns.DATA;
                        String imageLocation = cursor.getString(cursor.getColumnIndex(dataIndex));
                        File imageFile = new File(imageLocation);
                        pickerTiles.add(new PickerTile(Uri.fromFile(imageFile), 0, 0));
                        count++;
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }


    }

    public void setSelectedUriList(ArrayList<Uri> selectedUriList, Uri uri) {
        this.selectedUriList = selectedUriList;

        int position = -1;


        PickerTile pickerTile;
        for (int i = 0; i < pickerTiles.size(); i++) {
            pickerTile = pickerTiles.get(i);
            if (pickerTile.isImageTile() && pickerTile.getImageUri().equals(uri)) {
                position = i;
                break;
            }

        }


        if (position > 0) {
            notifyItemChanged(position);
        }


    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.bottompicker_grid_item, null);
        final GalleryViewHolder holder = new GalleryViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        PickerTile pickerTile = getItem(position);


        boolean isSelected = false;

        if (pickerTile.isCameraTile()) {
            holder.iv_thumbnail.setBackgroundResource(builder.cameraTileBackgroundResId);
            holder.iv_thumbnail.setImageDrawable(builder.cameraTileDrawable);
        } else if (pickerTile.isGalleryTile()) {
            holder.iv_thumbnail.setBackgroundResource(builder.galleryTileBackgroundResId);
            holder.iv_thumbnail.setImageDrawable(builder.galleryTileDrawable);

        } else {
            Uri uri = pickerTile.getImageUri();
            if (builder.imageProvider == null) {
                Glide.with(context)
                        .load(uri)
                        .thumbnail(0.1f)
                        .apply(new RequestOptions().centerCrop()
                                .placeholder(R.drawable.ic_gallary)
                                .error(R.drawable.img_error))
                        .into(holder.iv_thumbnail);
            } else {
                builder.imageProvider.onProvideImage(holder.iv_thumbnail, uri);
            }


            isSelected = selectedUriList.contains(uri);


        }


        if (holder.root instanceof FrameLayout) {

            Drawable foregroundDrawable;

            if (builder.selectedForegroundDrawable != null) {
                foregroundDrawable = builder.selectedForegroundDrawable;
            } else {
                foregroundDrawable = ContextCompat.getDrawable(context, R.drawable.gallery_photo_selected);
            }

            ((FrameLayout) holder.root).setForeground(isSelected ? foregroundDrawable : null);
        }


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    public PickerTile getItem(int position) {
        return pickerTiles.get(position);
    }

    @Override
    public int getItemCount() {
        return pickerTiles.size();
    }

    public void setOnItemClickListener(
            OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public static class PickerTile {

        public static final int IMAGE = 1;
        public static final int CAMERA = 2;
        public static final int GALLERY = 3;
        protected final Uri imageUri;
        protected final int imageHeight;
        protected final int imageWidth;
        protected final
        @TileType
        int tileType;

        PickerTile(@SpecialTileType int tileType) {
            this(null, 0, 0, tileType);
        }

        protected PickerTile(@Nullable Uri imageUri, int imageHeight, int imageWidth, @TileType int tileType) {
            this.imageUri = imageUri;
            this.tileType = tileType;
            this.imageHeight = imageHeight;
            this.imageWidth = imageWidth;
        }

        PickerTile(@NonNull Uri imageUri, int imageHeight, int imageWidth) {
            this(imageUri, imageHeight, imageWidth, IMAGE);
        }

        @Nullable
        public Uri getImageUri() {
            return imageUri;
        }

        @TileType
        public int getTileType() {
            return tileType;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        @Override
        public String toString() {
            if (isImageTile()) {
                return "ImageTile: " + imageUri;
            } else if (isCameraTile()) {
                return "CameraTile";
            } else if (isGalleryTile()) {
                return "PickerTile";
            } else {
                return "Invalid item";
            }
        }

        public boolean isImageTile() {
            return tileType == IMAGE;
        }

        public boolean isCameraTile() {
            return tileType == CAMERA;
        }

        public boolean isGalleryTile() {
            return tileType == GALLERY;
        }

        @IntDef({IMAGE, CAMERA, GALLERY})
        @Retention(RetentionPolicy.SOURCE)
        public @interface TileType {
        }

        @IntDef({CAMERA, GALLERY})
        @Retention(RetentionPolicy.SOURCE)
        public @interface SpecialTileType {
        }
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        SquareFrameLayout root;


        SquareImageView iv_thumbnail;

        public GalleryViewHolder(View view) {
            super(view);
            root = (SquareFrameLayout) view.findViewById(R.id.root);
            iv_thumbnail = (SquareImageView) view.findViewById(R.id.iv_thumbnail);

        }

    }


}
