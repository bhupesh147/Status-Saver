package com.example.statophobic.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statophobic.Adapter.WhatsAppAdapter;
import com.example.statophobic.Model.WhatsAppStatusModel;
import com.example.statophobic.R;
import com.example.statophobic.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class ImageFragment extends Fragment {

    private FragmentImageBinding binding;
    private ArrayList<WhatsAppStatusModel> list;
    private WhatsAppAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_image,container,false);

       list = new ArrayList<>();
       getData();

       binding.refreshLayout.setOnRefreshListener(()-> {
           list = new ArrayList<>();
           getData();
           binding.refreshLayout.setRefreshing(false);
        });

       return binding.getRoot();
    }

    private void getData() {
        WhatsAppStatusModel model;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
        File targetDir = new File(targetPath);
        File[] allFiles = targetDir.listFiles();

       /* String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.statuses";
        File targetDirBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness = targetDirBusiness.listFiles();*/
//

            Arrays.sort(allFiles,((ol, o2)->{
                if(ol.lastModified()>o2.lastModified()) return -1;
                else if(ol.lastModified()<o2.lastModified()) return +1;
                else return 0;
            }));

        for(int i=0;i<allFiles.length;i++){
            File file = allFiles[i];
            if(Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")){
                model = new WhatsAppStatusModel("whats"+i,
                        Uri.fromFile(file),
                        allFiles[i].getAbsolutePath(),
                        file.getName());
                list.add(model);

            }
        }
       /* try {
            Arrays.sort(allFilesBusiness,((o1,o2)->{
                if(o1.lastModified()>o2.lastModified()) return -1;
                else if(o1.lastModified()<o2.lastModified()) return +1;
                else return 0;
            }));
        } catch (AssertionError e) {
            e.printStackTrace();
        }

        for(int i=0;i< allFilesBusiness.length;i++){
            File file = allFilesBusiness[i];
            if(Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")){
                model = new WhatsAppStatusModel("whatsBusiness"+i,
                        Uri.fromFile(file),
                        allFilesBusiness[i].getAbsolutePath(),
                        file.getName());
                list.add(model);

            }
        }*/
        adapter = new WhatsAppAdapter(list,getActivity());
        binding.whatsAppRecycler.setAdapter(adapter);

    }

}