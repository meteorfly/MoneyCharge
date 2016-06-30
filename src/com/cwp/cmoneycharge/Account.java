package com.cwp.cmoneycharge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle; 
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cwp.cmoneycharge.R;

import cwp.moneycharge.dao.AccountDAO;
import cwp.moneycharge.dao.IncomeDAO;
import cwp.moneycharge.dao.ItypeDAO;
import cwp.moneycharge.dao.NoteDAO;
import cwp.moneycharge.dao.PayDAO;
import cwp.moneycharge.dao.PtypeDAO;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.CustomDialog;

@SuppressLint("NewApi")
public class Account  extends Activity{
	Button register,login,modify,adminlogin,alldelete;
	TextView usernow;
	int userid;
	public Account()  {
		// TODO Auto-generated constructor stub
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);// ���ò����ļ�

		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		register=(Button)findViewById(R.id.accregister);
		login=(Button)findViewById(R.id.acclogin);
		modify=(Button)findViewById(R.id.accchange);
		usernow=(TextView)findViewById(R.id.usernow);
		adminlogin=(Button)findViewById(R.id.accadminlogin);
		alldelete=(Button)findViewById(R.id.alldelete);
		
		
	}
	@Override
	public void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		AccountDAO accountdao=new AccountDAO(Account.this);
		ColorDrawable drawable;  
		if(userid==100000001){
			drawable = new ColorDrawable(0xff6e6e6e);
			
			adminlogin.setEnabled(false);
		}else{
			drawable = new ColorDrawable(0xff21a0a0);
			
			adminlogin.setEnabled(true);}
		usernow.setText(accountdao.find(userid));
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Register.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Login.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, ChangePwd.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		adminlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Index.class);
				intent.putExtra("cwp.id",100000001);
				Toast.makeText(Account.this,"���л�ΪĬ���û�", Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		alldelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteDialog();
				
			}
		});
	}
	private void deleteDialog(){  //�˳�����ķ��� 
		 Dialog dialog =null;

		 CustomDialog.Builder customBuilder = new  CustomDialog.Builder(Account.this);


		 customBuilder.setTitle("����")  // ��������

	        .setMessage("�˲�����ɾ����ǰ�û������û��������ݣ�ȷ��ɾ����")    //��ʾ�Ի��������
	        
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

	           public void onClick(DialogInterface dialog, int which) {
	        	   if(userid!=100000001){
		        	   AccountDAO accountdao=new AccountDAO(Account.this);
		        	   PayDAO paydao=new PayDAO(Account.this);
		        	   IncomeDAO incomedao=new IncomeDAO(Account.this);
		        	   ItypeDAO itypedao=new ItypeDAO(Account.this);
		        	   PtypeDAO ptypedao=new PtypeDAO(Account.this);
		        	   NoteDAO notedao=new NoteDAO(Account.this);
		        	   notedao.deleteUserData(userid);
		        	   ptypedao.deleteById(userid);
		        	   itypedao.deleteById(userid);
		        	   paydao.deleteUserData(userid);
		        	   incomedao.deleteUserData(userid);
		        	   accountdao.deleteById(userid);
		        	   
		        	   Intent intent = new Intent(Account.this, Index.class);
		        	   intent.putExtra("cwp.id",100000001);
					   Toast.makeText(Account.this,"ɾ���ɹ����ѵ�½Ĭ���û�", Toast.LENGTH_LONG).show();
					   startActivity(intent);
	        	   }
	        	   else{

					   Toast.makeText(Account.this,"Ĭ���û�������ɾ����", Toast.LENGTH_LONG).show();
		           	   dialog.dismiss();
	        	   }
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
	    	Intent intent=new Intent(Account.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
