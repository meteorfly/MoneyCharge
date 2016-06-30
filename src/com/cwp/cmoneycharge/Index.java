package com.cwp.cmoneycharge;

import java.util.ArrayList;
import java.util.List;
   
import com.cwp.cmoneycharge.R;

import cwp.moneycharge.dao.AccountDAO;
import cwp.moneycharge.dao.DBOpenHelper;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.CustomDialog;
import cwp.moneycharge.model.Tb_account;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Index extends Activity {  
	GridView gvInfo;// 创建GridView对象 
	int userid;
	int returnflag;
	// 定义字符串数组，存储系统功能
	String[] titles = new String[] { "我的支出", "我的收入", "我的便签", "数据统计", "账户管理",
			"系统设置",  "退出" };
	// 定义int数组，存储功能对应的图标
	int[] images = new int[] { R.drawable.pay,
			R.drawable.income, R.drawable.note,
			R.drawable.data, R.drawable.account, R.drawable.setting,
			R.drawable.exit };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		gvInfo = (GridView) findViewById(R.id.gvInfo);// 获取布局文件中的gvInfo组件
		pictureAdapter adapter = new pictureAdapter(titles, images, this);// 创建pictureAdapter对象
		gvInfo.setAdapter(adapter);// 为GridView设置数据源 
	}
	@Override
	protected void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id", 100000001);//默认用户
		returnflag=0;
		gvInfo.setOnItemClickListener(new OnItemClickListener() {// 为GridView设置项单击事件
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = null;// 创建Intent对象
				switch (arg2) {
				case 0://我的支出
					intent = new Intent(Index.this, Pay.class);// 使用AddOutaccount窗口初始化Intent
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 1://我的收入
					intent = new Intent(Index.this, Income.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 2://我的便签
					intent = new Intent(Index.this, Note.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 3://数据统计
					intent = new Intent(Index.this, Data.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent);  
					break;
				case 4://账户管理
					intent = new Intent(Index.this, Account.class);
					intent.putExtra("cwp.id", userid);
					startActivity(intent);  
					break;
				case 5://系统设置
					intent = new Intent(Index.this, Setting.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 6: //退出
				
					
					exitDialog();// 关闭当前Activity
				
				}	
			}
		});
	}
	/**
	 * Build the desired Dialog
	 * CUSTOM or DEFAULT
	 */
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}
	class ViewHolder// 创建ViewHolder类
	{
		public TextView title;// 创建TextView对象
		public ImageView image;// 创建ImageView对象
	}
	public void quit() {
		ActivityManager.getInstance().exit();
	}
	 private void exitDialog(){  //退出程序的方法 
		 Dialog dialog =null;

		 CustomDialog.Builder customBuilder = new  CustomDialog.Builder(Index.this);


		 customBuilder.setTitle("退出程序")  // 创建标题

	        .setMessage("您确定要退出吗？")    //表示对话框的内容
	        
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

	           public void onClick(DialogInterface dialog, int which) {
	        	   quit();
	            }

	       }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
	    	    
               public void onClick(DialogInterface dialog, int which) {
            	   dialog.dismiss();
            	  
                }
	         });
		 	dialog=customBuilder.create();//创建对话框 
		 	dialog.show();  //显示对话框

	  }
	 

		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
		    	if(returnflag==0){
		    		Toast.makeText(Index.this,"再按一次退出程序！", Toast.LENGTH_SHORT).show();
		    		returnflag=1;
		    	}else{
		    		quit();
		    	}
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}

}
class pictureAdapter extends BaseAdapter// 创建基于BaseAdapter的子类
{
	private LayoutInflater inflater;// 创建LayoutInflater对象
	private List<Picture> pictures;// 创建List泛型集合

	// 为类创建构造函数
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();// 初始化泛型集合对象
		inflater = LayoutInflater.from(context);// 初始化LayoutInflater对象
		for (int i = 0; i < images.length; i++)// 遍历图像数组
		{
			Picture picture = new Picture(titles[i], images[i]);// 使用标题和图像生成Picture对象
			pictures.add(picture);// 将Picture对象添加到泛型集合中
		}
	}

	@Override
	public int getCount() {// 获取泛型集合的长度
		if (null != pictures) {// 如果泛型集合不为空
			return pictures.size();// 返回泛型长度
		} else {
			return 0;// 返回0
		}
	}

	@Override
	public Object getItem(int arg0) {
		return pictures.get(arg0);// 获取泛型集合指定索引处的项
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;// 返回泛型集合的索引
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;// 创建ViewHolder对象
		if (arg1 == null)// 判断图像标识是否为空
		{
			arg1 = inflater.inflate(R.layout.gvitem, null);// 设置图像标识
			viewHolder = new ViewHolder();// 初始化ViewHolder对象
			viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);// 设置图像标题
			viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);// 设置图像的二进制值
			arg1.setTag(viewHolder);// 设置提示
		} else {
			viewHolder = (ViewHolder) arg1.getTag();// 设置提示
		}
		viewHolder.title.setText(pictures.get(arg0).getTitle());// 设置图像标题
		viewHolder.image.setImageResource(pictures.get(arg0).getImageId());// 设置图像的二进制值
		return arg1;// 返回图像标识
	}
	
}

class ViewHolder// 创建ViewHolder类
{
	public TextView title;// 创建TextView对象
	public ImageView image;// 创建ImageView对象
}
class Picture// 创建Picture类
{
	private String title;// 定义字符串，表示图像标题
	private int imageId;// 定义int变量，表示图像的二进制值

	public Picture()// 默认构造函数
	{
		super();
	}

	public Picture(String title, int imageId)// 定义有参构造函数
	{
		super();
		this.title = title;// 为图像标题赋值
		this.imageId = imageId;// 为图像的二进制值赋值
	}

	public String getTitle() {// 定义图像标题的可读属性
		return title;
	}

	public void setTitle(String title) {// 定义图像标题的可写属性
		this.title = title;
	}

	public int getImageId() {// 定义图像二进制值的可读属性
		return imageId;
	}

	public void setimageId(int imageId) {// 定义图像二进制值的可写属性
		this.imageId = imageId;
	}
	
}
