		
		/**
		 * 
		 */
		package com.cwp.cmoneycharge;
		
		/**
		 * @author cwpcc
		 *
		 */ 
		import com.cwp.cmoneycharge.R;

import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_account;
import cwp.moneycharge.dao.AccountDAO;
					
					import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
		@SuppressLint("NewApi")
		
		public class Login extends Activity {
			EditText tusername;
			EditText tpwd;
			Button blogin,bcancle;
			Intent intentr;
			int userid;
			
			public Login() {
				// TODO Auto-generated constructor stub
			}
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.login);// ���ò����ļ�

				ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
				tusername = (EditText) findViewById(R.id.username);// ��ȡ�û���
				tpwd= (EditText) findViewById(R.id.password);// ��ȡ�û���
				blogin = (Button) findViewById(R.id.btnLogin);// ��ȡ��¼��ť
				bcancle = (Button) findViewById(R.id.btnCancle);// ��ȡȡ����ť							
					
			
		       
			}
		 @Override
        protected void onStart(){
				super.onStart(); 
				intentr=getIntent();
				userid=intentr.getIntExtra("cwp.id",100000001);
				blogin.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Login.this, Index.class);// ����Intent����
						AccountDAO accountDAO = new AccountDAO(Login.this);// ����AccountDAO����
						Tb_account account=accountDAO.find(tusername.getText().toString(),tpwd.getText().toString());
						// �ж��Ƿ������뼰�Ƿ�����������
						if(account!=null ){ 
							intent.putExtra("cwp.id", account.get_id());
							startActivity(intent);// ������Activity
						} else if(tusername.getText().toString().isEmpty()||tpwd.getText().toString().isEmpty()) {
								Toast.makeText(Login.this, "�û��������벻Ϊ�գ�",
										Toast.LENGTH_LONG).show();
								tpwd.setText("");
							}else{
							
								// ������Ϣ��ʾ
								Toast.makeText(Login.this, "�û������������",
										Toast.LENGTH_LONG).show();
								tpwd.setText("");
							}
						
					}
					
				});
		        bcancle.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
		        	@Override
		        	public void onClick(View arg0){
		        		Intent intent = new Intent(Login.this, Account.class);// ����Intent����
						intent.putExtra("cwp.id",userid);
		        		startActivity(intent);// ������Activity
		        	}
		        });
		        tusername.setOnClickListener(new OnClickListener() {
		        	@Override
		        	public void onClick(View arg0){
		        		tusername.setText("");
		        		tpwd.setText("");
		        	}
		        });
		        tpwd.setOnClickListener(new OnClickListener() {
		        	@Override
		        	public void onClick(View arg0){
		        		tpwd.setText("");
		        	}
		        });
        }
	}
		
