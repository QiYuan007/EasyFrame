package demo.myframework.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qy.easyframe.common.ResultSubscriber;
import com.qy.easyframe.model.IModel;

import demo.myframework.R;
import demo.myframework.http.HttpRequest;
import demo.myframework.model.WeatherResponse;


public class MainActivity extends BaseActivity implements ResultSubscriber.OnResultListener{
    private static final String TAG = "MainActivity";
    private static final int CODE = 1;

    private Button mButton;
    private TextView mTextView;

    @Override
    protected int preView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textview);
    }

    @Override
    protected void initData() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClick");
                HttpRequest.getInstance().getWeather("101010300.html",CODE,MainActivity.this);
            }
        });
    }

    /**
     * 网络请求前调用，通常显示Progressialog
     * @param requestType
     */
    @Override
    public void onStart(int requestType) {
        Log.i(TAG,"onStart");
        showProgressDialog("");
    }

    /**
     * 网络请求错误后调用
     * @param requestType
     */
    @Override
    public void onError(int requestType, Throwable e) {
        Log.i(TAG,"onError");

        dismissProgressDialog();
    }

    /**
     * onResult 方法中处理请求下来的数据
     * @param model
     * @param requestType
     */
    @Override
    public void onResult(IModel model, int requestType) {
        Log.i(TAG,"onNext"+requestType);
        dismissProgressDialog();
        if (requestType == CODE){
            mTextView.setText(((WeatherResponse)model).getWeatherinfo().toString());
        }
    }

}
