package com.cwp.cmoneycharge;
 
import cwp.moneycharge.dao.DBOpenHelper;
import cwp.moneycharge.dao.IncomeDAO;
import cwp.moneycharge.dao.ItypeDAO;
import cwp.moneycharge.dao.NoteDAO;
import cwp.moneycharge.dao.PayDAO;
import cwp.moneycharge.dao.PtypeDAO;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.CustomDialog;
import android.app.Activity; 
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.View;

public class Setting extends Activity{
	int userid;
	Intent intentr;
	private ListView listview;  
	public Setting() {
		// TODO Auto-generated constructor stub
	} 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		 listview=(ListView)findViewById(R.id.settinglisv);  
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.settingtype,android.R.layout.simple_expandable_list_item_1 );
		
		listview.setAdapter(adapter); 
	}
	@Override
	protected void onStart(){
		
		super.onStart();
		intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		listview.setOnItemClickListener(new OnItemClickListener() {// ΪGridView��������¼�
			 @Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) { 	
				String result=arg0.getItemAtPosition(pos).toString();  
				Intent intent = getIntent();// ����Intent����
				userid=intent.getIntExtra("cwp.id",100000001);
				switch (pos) {
				case 0: 
					alarmDialog(pos);//�����������
					break;
				case 1: 
					alarmDialog(pos); //���֧������
					break;
				case 2: 
					alarmDialog(pos); //��ձ�ǩ����
					break;
				case 3: 
					intentr=new Intent(Setting.this,InPtypeManager.class);
					intentr.putExtra("cwp.id",userid);
					intentr.putExtra("type", 0);
					startActivity(intentr);
					break;
				case 4: 
					intentr=new Intent(Setting.this,InPtypeManager.class);
					intentr.putExtra("cwp.id",userid);
					intentr.putExtra("type", 1);
					startActivity(intentr);
					break;
				case 5:   
					alarmDialog(pos); 	//���ݳ�ʼ��				
					break; 
				case 6: 
					//����ϵͳ
					intentr=new Intent(Setting.this,About.class);
					intentr.putExtra("cwp.id",userid);
					startActivity(intentr);
					break; 
				
				} 
			}
		
		});
	}
	private void alarmDialog(int type){  //�˳�����ķ��� 
		 Dialog dialog =null;
		 String ps="��������",is="֧������",ns="��ǩ����";
		 CustomDialog.Builder customBuilder = new  CustomDialog.Builder(Setting.this);


		 customBuilder.setTitle("����");  // ��������
		 switch(type){
		 	case 0:
				 customBuilder.setMessage("��ɾ����ǰ���û�����"+is).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	
			           public void onClick(DialogInterface dialog, int which) { 
			        	   	IncomeDAO incomeDAO=new IncomeDAO(Setting.this);
			        	   	incomeDAO.deleteUserData(userid); 
							Toast.makeText(Setting.this,"�����~����",Toast.LENGTH_LONG).show();
			            }
	
			       }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			    	    
		              public void onClick(DialogInterface dialog, int which) {
		           	   dialog.dismiss();
		           	  
		               }
			         });
				 break;
		 
		 	case 1:
				 customBuilder.setMessage("��ɾ����ǰ���û�����"+ps).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	
			           public void onClick(DialogInterface dialog, int which) { 
			        	   	PayDAO payDAO=new PayDAO(Setting.this);
			        	   	payDAO.deleteUserData(userid);
							Toast.makeText(Setting.this,"�����~����",Toast.LENGTH_LONG).show();	
							 dialog.dismiss();
			            }
	
			       }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			    	    
		              public void onClick(DialogInterface dialog, int which) {
		           	   dialog.dismiss();
		           	  
		               }
			         });
				 break;
		 	case 2:
				 customBuilder.setMessage("��ɾ����ǰ���û�����"+ps).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
			           public void onClick(DialogInterface dialog, int which) { 
			        	   	NoteDAO noteDAO=new NoteDAO(Setting.this);
			        	   	noteDAO.deleteUserData(userid);
							Toast.makeText(Setting.this,"�����~����",Toast.LENGTH_LONG).show();
							 dialog.dismiss();
			            }
	
			       }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			    	    
		              public void onClick(DialogInterface dialog, int which) {
		           	   dialog.dismiss();
		           	  
		               }
			         });
				 break;
		 	case 5:
				 customBuilder.setMessage("�˲��������õ�ǰ�û������롢֧�����ͣ�ȷ����ԭ��").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
			           public void onClick(DialogInterface dialog, int which) { 
			        	   ItypeDAO itypedao=new ItypeDAO(Setting.this);
							PtypeDAO ptypedao=new PtypeDAO(Setting.this);
							itypedao.initData(userid);
							ptypedao.initData(userid);
							Toast.makeText(Setting.this,"�ѻ�ԭ~����",Toast.LENGTH_LONG).show();
							 dialog.dismiss();
			            }
	
			       }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			    	    
		              public void onClick(DialogInterface dialog, int which) {
		           	   dialog.dismiss();
		           	  
		               }
			         });
				 break;
			 
		 }
	        
		
		 	dialog=customBuilder.create();//�����Ի��� 
		 	dialog.show();  //��ʾ�Ի���

	  }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	    	Intent intent=new Intent(Setting.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
