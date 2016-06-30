package com.cwp.cmoneycharge;
 
import cwp.moneycharge.model.ActivityManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Data extends Activity {
	Button paydata,incomedata,pidata; 
	int userid;
	public Data() {
		// TODO Auto-generated constructor stub
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);// 设置布局文件
		
		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		
		incomedata=(Button)findViewById(R.id.incomedata);
		pidata=(Button)findViewById(R.id.pidata); 
		
	
	
		incomedata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Data.this, IncomeData.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		pidata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Data.this, PIData.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		 
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	    	Intent intent=new Intent(Data.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
