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
	GridView gvInfo;// ����GridView���� 
	int userid;
	int returnflag;
	// �����ַ������飬�洢ϵͳ����
	String[] titles = new String[] { "�ҵ�֧��", "�ҵ�����", "�ҵı�ǩ", "����ͳ��", "�˻�����",
			"ϵͳ����",  "�˳�" };
	// ����int���飬�洢���ܶ�Ӧ��ͼ��
	int[] images = new int[] { R.drawable.pay,
			R.drawable.income, R.drawable.note,
			R.drawable.data, R.drawable.account, R.drawable.setting,
			R.drawable.exit };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		gvInfo = (GridView) findViewById(R.id.gvInfo);// ��ȡ�����ļ��е�gvInfo���
		pictureAdapter adapter = new pictureAdapter(titles, images, this);// ����pictureAdapter����
		gvInfo.setAdapter(adapter);// ΪGridView��������Դ 
	}
	@Override
	protected void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id", 100000001);//Ĭ���û�
		returnflag=0;
		gvInfo.setOnItemClickListener(new OnItemClickListener() {// ΪGridView��������¼�
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = null;// ����Intent����
				switch (arg2) {
				case 0://�ҵ�֧��
					intent = new Intent(Index.this, Pay.class);// ʹ��AddOutaccount���ڳ�ʼ��Intent
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 1://�ҵ�����
					intent = new Intent(Index.this, Income.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 2://�ҵı�ǩ
					intent = new Intent(Index.this, Note.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 3://����ͳ��
					intent = new Intent(Index.this, Data.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent);  
					break;
				case 4://�˻�����
					intent = new Intent(Index.this, Account.class);
					intent.putExtra("cwp.id", userid);
					startActivity(intent);  
					break;
				case 5://ϵͳ����
					intent = new Intent(Index.this, Setting.class); 
					intent.putExtra("cwp.id", userid);
					startActivity(intent); 
					break;
				case 6: //�˳�
				
					
					exitDialog();// �رյ�ǰActivity
				
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
	class ViewHolder// ����ViewHolder��
	{
		public TextView title;// ����TextView����
		public ImageView image;// ����ImageView����
	}
	public void quit() {
		ActivityManager.getInstance().exit();
	}
	 private void exitDialog(){  //�˳�����ķ��� 
		 Dialog dialog =null;

		 CustomDialog.Builder customBuilder = new  CustomDialog.Builder(Index.this);


		 customBuilder.setTitle("�˳�����")  // ��������

	        .setMessage("��ȷ��Ҫ�˳���")    //��ʾ�Ի��������
	        
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

	           public void onClick(DialogInterface dialog, int which) {
	        	   quit();
	            }

	       }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
	    	    
               public void onClick(DialogInterface dialog, int which) {
            	   dialog.dismiss();
            	  
                }
	         });
		 	dialog=customBuilder.create();//�����Ի��� 
		 	dialog.show();  //��ʾ�Ի���

	  }
	 

		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
		    	if(returnflag==0){
		    		Toast.makeText(Index.this,"�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
		    		returnflag=1;
		    	}else{
		    		quit();
		    	}
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}

}
class pictureAdapter extends BaseAdapter// ��������BaseAdapter������
{
	private LayoutInflater inflater;// ����LayoutInflater����
	private List<Picture> pictures;// ����List���ͼ���

	// Ϊ�ഴ�����캯��
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();// ��ʼ�����ͼ��϶���
		inflater = LayoutInflater.from(context);// ��ʼ��LayoutInflater����
		for (int i = 0; i < images.length; i++)// ����ͼ������
		{
			Picture picture = new Picture(titles[i], images[i]);// ʹ�ñ����ͼ������Picture����
			pictures.add(picture);// ��Picture������ӵ����ͼ�����
		}
	}

	@Override
	public int getCount() {// ��ȡ���ͼ��ϵĳ���
		if (null != pictures) {// ������ͼ��ϲ�Ϊ��
			return pictures.size();// ���ط��ͳ���
		} else {
			return 0;// ����0
		}
	}

	@Override
	public Object getItem(int arg0) {
		return pictures.get(arg0);// ��ȡ���ͼ���ָ������������
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;// ���ط��ͼ��ϵ�����
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;// ����ViewHolder����
		if (arg1 == null)// �ж�ͼ���ʶ�Ƿ�Ϊ��
		{
			arg1 = inflater.inflate(R.layout.gvitem, null);// ����ͼ���ʶ
			viewHolder = new ViewHolder();// ��ʼ��ViewHolder����
			viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);// ����ͼ�����
			viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);// ����ͼ��Ķ�����ֵ
			arg1.setTag(viewHolder);// ������ʾ
		} else {
			viewHolder = (ViewHolder) arg1.getTag();// ������ʾ
		}
		viewHolder.title.setText(pictures.get(arg0).getTitle());// ����ͼ�����
		viewHolder.image.setImageResource(pictures.get(arg0).getImageId());// ����ͼ��Ķ�����ֵ
		return arg1;// ����ͼ���ʶ
	}
	
}

class ViewHolder// ����ViewHolder��
{
	public TextView title;// ����TextView����
	public ImageView image;// ����ImageView����
}
class Picture// ����Picture��
{
	private String title;// �����ַ�������ʾͼ�����
	private int imageId;// ����int��������ʾͼ��Ķ�����ֵ

	public Picture()// Ĭ�Ϲ��캯��
	{
		super();
	}

	public Picture(String title, int imageId)// �����вι��캯��
	{
		super();
		this.title = title;// Ϊͼ����⸳ֵ
		this.imageId = imageId;// Ϊͼ��Ķ�����ֵ��ֵ
	}

	public String getTitle() {// ����ͼ�����Ŀɶ�����
		return title;
	}

	public void setTitle(String title) {// ����ͼ�����Ŀ�д����
		this.title = title;
	}

	public int getImageId() {// ����ͼ�������ֵ�Ŀɶ�����
		return imageId;
	}

	public void setimageId(int imageId) {// ����ͼ�������ֵ�Ŀ�д����
		this.imageId = imageId;
	}
	
}
