package com.faiz.managesystem.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.faiz.managesystem.R;

public class CustomProgressDialog extends Dialog {

    public TextView messageTv;

//    public CustomProgressDialog(Context context) {
//        this(context, R.style.MyProgressDialogStyle, "");
//    }

    public CustomProgressDialog(Context context, String string) {
        this(context, R.style.MyDialogStyle, string);

    }

    public CustomProgressDialog(Context context, int theme, String string) {
        super(context, theme);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_custom_progress);
        messageTv = (TextView) findViewById(R.id.tv_message);
        messageTv.setText(string);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount = 0.5f;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
