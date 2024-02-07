package com.example.kingearnadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.kingearnadmin.R;
import com.example.kingearnadmin.Utils.SketchwareUtil;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.widget.AdapterView;
import android.graphics.Typeface;


public class SubscriptionActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> m = new HashMap<>();
	private String k = "";
	private String memberships = "";
	
	private ArrayList<String> spin = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear8;
	private LinearLayout linear3;
	private Spinner spinner1;
	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	private LinearLayout linear9;
	private LinearLayout linear11;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	private TextView textview6;
	private TextView textview7;
	private TextView textview8;
	private TextView textview12;
	private TextView textview13;
	private TextView textview14;
	private TextView textview15;
	private TextView textview16;
	private TextView textview17;
	
	private DatabaseReference membership = _firebase.getReference("membership");
	private ChildEventListener _membership_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.subscription);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		textview1 = (TextView) findViewById(R.id.textview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview7 = (TextView) findViewById(R.id.textview7);
		textview8 = (TextView) findViewById(R.id.textview8);
		textview12 = (TextView) findViewById(R.id.textview12);
		textview13 = (TextView) findViewById(R.id.textview13);
		textview14 = (TextView) findViewById(R.id.textview14);
		textview15 = (TextView) findViewById(R.id.textview15);
		textview16 = (TextView) findViewById(R.id.textview16);
		textview17 = (TextView) findViewById(R.id.textview17);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 1) {
					memberships = "silver";
				}
				if (_position == 2) {
					memberships = "gold";
				}
				if (_position == 3) {
					memberships = "diamond";
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("")) {
					((EditText)edittext1).setError("Enter Number");
				}
				else {
					if (edittext2.getText().toString().equals("")) {
						((EditText)edittext2).setError("Enter Amount");
					}
					else {
						if (memberships.equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Select Membership");
						}
						else {
							m = new HashMap<>();
							m.put("fee", edittext1.getText().toString());
							m.put("ad", edittext2.getText().toString());
							if (memberships.equals("silver")) {
								m.put("membership", "Silver");
							}
							if (memberships.equals("gold")) {
								m.put("membership", "Gold");
							}
							if (memberships.equals("diamond")) {
								m.put("membership", "Diamond");
							}
							membership.push().updateChildren(m);
							m.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "save");
							edittext1.setText("");
							edittext2.setText("");
							memberships = "";
						}
					}
				}
			}
		});
		
		_membership_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("membership").toString().equals("Silver")) {
					if (_childValue.containsKey("fee")) {
						textview7.setText(_childValue.get("fee").toString());
					}
					if (_childValue.containsKey("ad")) {
						textview8.setText(_childValue.get("ad").toString());
					}
				}
				if (_childValue.get("membership").toString().equals("Gold")) {
					if (_childValue.containsKey("fee")) {
						textview13.setText(_childValue.get("fee").toString());
					}
					if (_childValue.containsKey("ad")) {
						textview14.setText(_childValue.get("ad").toString());
					}
				}
				if (_childValue.get("membership").toString().equals("Diamond")) {
					if (_childValue.containsKey("fee")) {
						textview16.setText(_childValue.get("fee").toString());
					}
					if (_childValue.containsKey("ad")) {
						textview17.setText(_childValue.get("ad").toString());
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		membership.addChildEventListener(_membership_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("Subscription");
		_rippleRoundStroke(textview1, "#F6910F", "#FFFFFF", 15, 0, "#FFFFFF");
		_rippleRoundStroke(linear2, "#FFFFFF", "#FFFFFF", 15, 0, "#FFFFFF");
		_rippleRoundStroke(linear8, "#FFFFFF", "#FFFFFF", 15, 0, "#FFFFFF");
		spin.add("Select Membership");
		spin.add("Silver");
		spin.add("Gold");
		spin.add("Diamond");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spin));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), Typeface.NORMAL);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), Typeface.NORMAL);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), Typeface.NORMAL);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview12.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview13.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview14.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview15.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview16.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
		textview17.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF757575")}), GG, null);
		_view.setBackground(RE);
		_view.setElevation(5);
	}
	
	
	public void _loadingdialog (final boolean _ifShow, final String _title) {
		if (_ifShow) {
			if (prog == null){
				prog = new ProgressDialog(this);
				prog.setMax(100);
				prog.setIndeterminate(true);
				prog.setCancelable(false);
				prog.setCanceledOnTouchOutside(false);
			}
			prog.setMessage(_title);
			prog.show();
			
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
			
			
		}
	} private ProgressDialog prog; {
		
		
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
