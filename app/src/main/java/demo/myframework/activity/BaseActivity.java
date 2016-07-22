package demo.myframework.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import demo.myframework.R;


public abstract class BaseActivity extends FragmentActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(preView());
        // showTitleLeftIcon(this);
        initView();
        initData();

    }

    protected abstract int preView();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 隐藏TitleBar左侧图标
     */
    protected void visibleLeftIcon() {
        ImageView leftIv = (ImageView) findViewById(R.id.title_left_iv);
        leftIv.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置TitleBar左侧图标
     *
     * @param id 资源ID
     */
    protected void setLefIcon(int id) {
        ImageView leftIv = (ImageView) findViewById(R.id.title_left_iv);
        leftIv.setBackgroundResource(id);
    }

    /**
     * 设置TitleBar名称
     *
     * @param name
     */
    protected void setTitleName(String name) {
        TextView title = (TextView) findViewById(R.id.content);
        title.setText(name);
    }

    protected void showTitleLeftIcon(final BaseActivity context) {
        ImageView leftIv = (ImageView) findViewById(R.id.title_left_iv);
        if (leftIv == null) {
            return;
        }
        // findViewById(R.id.title_left_iv).setVisibility(View.VISIBLE);
        leftIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }

    protected void showTitleRightIcon(final BaseActivity context,
                                      OnClickListener onClickListener) {
        ImageView rightIv = (ImageView) findViewById(R.id.title_right_iv);
        if (rightIv == null) {
            return;
        }
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setOnClickListener(onClickListener);
    }

    protected void showTitleRegistIcon(final BaseActivity context,
                                       OnClickListener onClickListener) {
        View registIv = (View) findViewById(R.id.title_mine);
        if (registIv == null) {
            return;
        }
        registIv.setVisibility(View.VISIBLE);
        registIv.setOnClickListener(onClickListener);
    }

    /**
     * 显示加载动画
     *
     * @param message
     */
    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    /**
     * 取消加载动画
     */
    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing())
                mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        dismissProgressDialog();
        super.onPause();
    }

    protected void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("努力加载中，请稍后…")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                }).setCancelable(false).create().show();
    }

    protected void exit(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                }).setCancelable(false).create().show();
    }


//	protected void loadData(int dataType, OnLoadListener listener) {
//		AsyncLoader loader = new AsyncLoader(dataType);
//		loader.setOnLoadListener(listener);
//		loader.execute();
//	}
//
//	protected String getImeiMac() {
//		return ContentManager.getInstance().getImei()
//				+ ContentManager.getInstance().getMac();
//	}

}
