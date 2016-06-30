
package com.cwp.cmoneycharge;

/**
 * @author cwpcc
 *
 */
import com.cwp.cmoneycharge.R;

import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_account;
import cwp.moneycharge.dao.AccountDAO;
import cwp.moneycharge.dao.ItypeDAO;
import cwp.moneycharge.dao.PtypeDAO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Register extends Activity{
	EditText tusername;
	EditText tpwd,ttpwd;
	Button bregister,bcancle;
	Intent intentr;
	int userid;
	public Register() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);// ���ò����ļ�

		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		tusername=(EditText)findViewById(R.id.rusername);
		tpwd=(EditText)findViewById(R.id.rpassword);
		ttpwd=(EditText)findViewById(R.id.rrpassword);
		bregister=(Button)findViewById(R.id.btnrRegister);
		bcancle=(Button)findViewById(R.id.btnrCancle);
		tusername.setText("");
		tpwd.setText("");
		ttpwd.setText("");
		
	}
	@Override
	protected void onStart(){
		super.onStart();
		intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		bregister.setOnClickListener(new OnClickListener(){
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Register.this, Index.class);// ����Intent����
				AccountDAO accountDAO = new AccountDAO(Register.this);// ����AccountDAO����
				// �ж��Ƿ������뼰�Ƿ�����������
				if(tusername.getText().toString().isEmpty()||tpwd.getText().toString().isEmpty() ||ttpwd.getText().toString().isEmpty() ){
					// ������Ϣ��ʾ
					Toast.makeText(Register.this, "�뽫��Ϣ��д������",
							Toast.LENGTH_LONG).show(); 
				} else if(tpwd.getText().toString().equals(ttpwd.getText().toString())){
						userid=accountDAO.add(new Tb_account(0,tusername.getText().toString(),tpwd.getText().toString()));
						ItypeDAO itypedao=new ItypeDAO(Register.this);
						PtypeDAO ptypedao=new PtypeDAO(Register.this);
						itypedao.initData(userid);
						ptypedao.initData(userid);
						Toast.makeText(Register.this, "�ѵ�¼��"+tusername.getText().toString(),
								Toast.LENGTH_LONG).show();  
						intent.putExtra("cwp.id", userid);
						startActivity(intent);// ������Activity
				 }else{ 
						Toast.makeText(Register.this, "������������벻һ�£���",
								Toast.LENGTH_LONG).show();
						tpwd.setText("");
						ttpwd.setText("");
					}
				
			}
			
		});
        bcancle.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
        	@Override
        	public void onClick(View arg0){
				Intent intent = new Intent(Register.this, Account.class);// ����Intent����
				intent.putExtra("cwp.id",userid);
				startActivity(intent);// ������Activity
        	}
        });
	}
}
