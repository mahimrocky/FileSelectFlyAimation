package com.skyhope.filechooseranimation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 4/24/2019 at 12:07 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 4/24/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */


public class FileSelectAnimationAdapter extends RecyclerView.Adapter<FileSelectAnimationAdapter.FileAnimationVH> {

    private List<FileContainer> fileContainerList;
    private Context context;
    private ItemClickListener listener;

    public FileSelectAnimationAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.fileContainerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FileAnimationVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file_select, viewGroup, false);
        return new FileAnimationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileAnimationVH holder, int i) {
        final FileContainer container = fileContainerList.get(i);

        holder.fileName.setText(container.getFileName());
        holder.imageViewMain.setImageDrawable(container.getAppIcon());
        holder.imageViewAnim.setImageDrawable(container.getAppIcon());

        holder.imageViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onGetItem(holder.imageViewAnim, container.getAppIcon());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileContainerList.size();
    }

    public void addItems(List<FileContainer> list) {
        if (fileContainerList != null) {
            fileContainerList.clear();
            fileContainerList = list;
            notifyDataSetChanged();
        }
    }

    class FileAnimationVH extends RecyclerView.ViewHolder {
        ImageView imageViewMain, imageViewAnim;
        TextView fileName;

        FileAnimationVH(@NonNull View itemView) {
            super(itemView);
            imageViewMain = itemView.findViewById(R.id.image_view_main);
            imageViewAnim = itemView.findViewById(R.id.image_view_anim);
            fileName = itemView.findViewById(R.id.text_view_file_name);

        }


    }

    interface ItemClickListener {
        void onGetItem(ImageView view, Drawable icon);
    }
}
