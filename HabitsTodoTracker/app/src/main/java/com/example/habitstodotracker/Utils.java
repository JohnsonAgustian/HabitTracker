package com.example.habitstodotracker;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Utils {
    public static Dialog progressDialog;
    public static void showProgress(Context context) {
        progressDialog = new Dialog(context, R.style.Base_Theme_AppCompat_Dialog);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView iv=new ImageView(context);

        Glide.with(context).asGif().load(R.raw.loadingbird)
                .apply(new RequestOptions().override(300,300))
                .into(iv);
        ViewGroup.MarginLayoutParams imageViewParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(imageViewParams);
        progressDialog.setContentView(iv);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        Window window = progressDialog.getWindow();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void cancelProgress(){
        progressDialog.dismiss();
    }
}
