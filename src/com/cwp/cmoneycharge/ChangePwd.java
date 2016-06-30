package com.cwp.cmoneycharge;
import com.cwp.cmoneycharge.R;

import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_account;
import cwp.moneycharge.dao.AccountDAO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
public class ChangePwd extends Activity{
	EditText cptusername,cpusername;
	EditText cptpwd,cppwd;
	Button bcpsure,bcpcancle,bcptchange,bcptcancle;
	String suserneme,spwd;
	LinearLayout L1,L2;
	Intent intentr;
	int userid;
	public ChangePwd() {
		// TODO Auto-generated constructor stub
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);// ���ò����ļ�

		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		cptusername=(EditText)findViewById(R.id.cptusername);
		cpusername=(EditText)findViewById(R.id.cpusername);
		cptpwd=(EditText)findViewById(R.id.cptpassword);
		cppwd=(EditText)findViewById(R.id.cppassword);
		bcpcancle=(Button)findViewById(R.id.btncpCancle);
		bcptcancle=(Button)findViewById(R.id.btncptCancle);
		bcpsure=(Button)findViewById(R.id.btncpsure);
		bcptchange=(Button)findViewById(R.id.btncptsure);
		L1=(LinearLayout)findViewById(R.id.cpyanzheng);
		L2=(LinearLayout)findViewById(R.id.cphint);
		L1.setVisibility(View.VISIBLE);
		L2.setVisibility(View.GONE);		
		
	}
	@Override
	protected void onStart(){ 
		super.onStart();
		intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		bcpsure.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				AccountDAO accountDAO = new AccountDAO(ChangePwd.this);// ����AccountDAO����
				suserneme=cpusername.getText().toString();
				spwd=cppwd.getText().toString();
				Tb_account tb_account=accountDAO.find(suserneme, spwd); 
				if(tb_account.get_id()==100000001){
					Toast.makeText(ChangePwd.this, "Ĭ���û��������޸��û������룡/n ����Ҫ����˽����Ϣ���뽨���˻���",
							Toast.LENGTH_LONG).show(); 
				} else if(accountDAO.find(suserneme, spwd)==null ){
					Toast.makeText(ChangePwd.this, "�û������������",
							Toast.LENGTH_LONG).show(); 
				} else {
					
						// ������Ϣ��ʾ
						
						cptusername.setText(suserneme);
						cptpwd.setText(spwd);
						L1.setVisibility(View.GONE);
						L2.setVisibility(View.VISIBLE);
				}
			}
		});
		bcptchange.setOnClickListener(new OnClickListener(){
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangePwd.this, Index.class);// ����Intent����
				AccountDAO accountDAO = new AccountDAO(ChangePwd.this);// ����AccountDAO����
				Tb_account tb_account= accountDAO.find(suserneme, spwd);
				if(cptusername.getText().toString().trim().isEmpty()||cptpwd.getText().toString().trim().isEmpty()){
					Toast.makeText(ChangePwd.this, "�뽫��Ϣ��д����~��",
							Toast.LENGTH_LONG).show(); 
				}else{ 
					accountDAO.update(tb_account.get_id(),cptusername.getText().toString(),cptpwd.getText().toString());
					Toast.makeText(ChangePwd.this, "�޸ĳɹ������Զ���½��"+cptusername.getText().toString(),
							Toast.LENGTH_LONG).show(); 
					intent.putExtra("cwp.id", tb_account.get_id());
					startActivity(intent);
				}
			}
		});
		bcpcancle.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) { 
				Intent intent = new Intent(ChangePwd.this, Account.class);// ����Intent����
				intent.putExtra("cwp.id",userid);
				startActivity(intent);// ������Activity
			}
		});
		bcptcancle.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) { 
				Intent intent = new Intent(ChangePwd.this, Account.class);// ����Intent����
				intent.putExtra("cwp.id",userid);
				startActivity(intent);// ������Activity
			}
		});
	}
}
