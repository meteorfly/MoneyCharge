package com.cwp.cmoneycharge;

import cwp.moneycharge.dao.AccountDAO;
import cwp.moneycharge.dao.IncomeDAO;
import cwp.moneycharge.dao.NoteDAO;
import cwp.moneycharge.dao.PayDAO;
import cwp.moneycharge.model.ActivityManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class About extends Activity {
	TextView usernow,countuser,countpay,countincome,countnote;
	TableRow author,description;
	Intent intentr ;
	int userid;
	AccountDAO accountDAO=new AccountDAO(About.this);
	PayDAO payDAO=new PayDAO(About.this);
	IncomeDAO incomeDAO=new IncomeDAO(About.this);
	NoteDAO noteDAO=new NoteDAO(About.this);
	
	public About() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActivityManager.getInstance().addActivity(this);
		usernow=(TextView)findViewById(R.id.useracc);
		countpay=(TextView)findViewById(R.id.countpay);
		countuser=(TextView)findViewById(R.id.countuser);
		countincome=(TextView)findViewById(R.id.countincome);
		countnote=(TextView)findViewById(R.id.countnote);
		author=(TableRow)findViewById(R.id.author);
		description=(TableRow)findViewById(R.id.description);
		
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		if(userid==100000001)
			usernow.setText("Ĭ���û�");
		else{  
			usernow.setText(accountDAO.find(userid));
		}
		
		countuser.setText(String.valueOf(accountDAO.getCount()));
		countnote.setText(String.valueOf(noteDAO.getCount(userid)));
		countpay.setText(String.valueOf(payDAO.getCount(userid)));
		countincome.setText(String.valueOf(incomeDAO.getCount(userid)));
		author.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(About.this,Author.class);
				intent.putExtra("cwp.id",userid);
				startActivity(intent);
			}
		});
		description.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(About.this,Description.class);
				intent.putExtra("cwp.id",userid);
				startActivity(intent);
			}
		});
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	    	Intent intent=new Intent(About.this,Setting.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
