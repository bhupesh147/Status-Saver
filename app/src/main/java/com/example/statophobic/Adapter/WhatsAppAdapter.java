package com.example.statophobic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statophobic.Model.WhatsAppStatusModel;
import com.example.statophobic.R;
import com.example.statophobic.Util;
import com.example.statophobic.databinding.WhatsappItemLayoutBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsAppAdapter extends RecyclerView.Adapter<WhatsAppAdapter.ViewHolder> {

    private ArrayList<WhatsAppStatusModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String saveFilePath = Util.RootDirectoryWhatsApp+"/";

    public WhatsAppAdapter(ArrayList<WhatsAppStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(inflater==null){
            inflater=LayoutInflater.from(parent.getContext());

        }

        return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.whatsapp_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhatsAppStatusModel item =list.get(position);

        if (item.getUri().toString().endsWith(".mp4"))
            holder.binding.playButton.setVisibility(View.VISIBLE);
        else
            holder.binding.playButton.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.binding.download.setOnClickListener(v -> {
            Util.createFileFolder();

            final String path = item.getPath();
            final File file = new File(path);
            File destination = new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file,destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Saved to :" +saveFilePath, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       WhatsappItemLayoutBinding binding;
       public ViewHolder(WhatsappItemLayoutBinding binding){
           super(binding.getRoot());

           this.binding =binding;
        }
    }
}
