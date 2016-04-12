package demo.myframework.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import demo.myframework.R;
import demo.myframework.common.ResultSubscriber;
import demo.myframework.http.HTTPHelper;
import demo.myframework.model.IModel;
import demo.myframework.model.WeatherModel;

public class MainActivity extends AppCompatActivity implements ResultSubscriber.OnResultListener<IModel>{
    private static final String TAG = "MainActivity";
    private static final int CODE = 1;

    private Button mButton;
    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClick");
                HTTPHelper.getInstance().getWeather("101010300",CODE,MainActivity.this);
            }
        });
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textview);
    }

    private ResultSubscriber setResultSubscriber(ResultSubscriber.OnResultListener listener){
        ResultSubscriber<IModel> subscriber = new ResultSubscriber<>(CODE);
        subscriber.setOnResultListener(listener);
        return subscriber;
    }

    /**
     * 网络请求前调用，通常显示Progressialog
     * @param requestType
     */
    @Override
    public void onStart(int requestType) {
        Log.i(TAG,"onStart");
    }

    /**
     * 网络请求完成调用，通常销毁Progressialog
     * @param requestType
     */
    @Override
    public void onCompleted(int requestType) {
        Log.i(TAG,"onCompleted");
    }

    /**
     * 网络请求错误后调用
     * @param e
     * @param requestType
     */
    @Override
    public void onError(Throwable e, int requestType) {
        Log.i(TAG,"onError");
    }

    /**
     * onNext 方法中处理请求下来的数据
     * @param iModel
     * @param requestType
     */
    @Override
    public void onNext(IModel iModel, int requestType) {
        Log.i(TAG,"onNext");
        if (requestType == CODE){
            mTextView.setText(((WeatherModel)iModel).getWeatherinfo().toString());
        }
    }
}
