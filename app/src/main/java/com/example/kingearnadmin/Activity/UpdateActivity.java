package com.example.kingearnadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.kingearnadmin.R;
import com.example.kingearnadmin.Utils.SketchwareUtil;
import com.google.android.material.appbar.AppBarLayout;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import android.widget.CompoundButton;


public class UpdateActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> c_map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> mape = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView textview2;
	private TextView textview3;
	private Switch switch1;
	private TextView textview4;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private Button button1;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext1;
	
	private DatabaseReference update = _firebase.getReference("update");
	private ChildEventListener _update_child_listener;
	private DatabaseReference control = _firebase.getReference("control");
	private ChildEventListener _control_child_listener;
	private Calendar c = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.update);
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
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		switch1 = (Switch) findViewById(R.id.switch1);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		button1 = (Button) findViewById(R.id.button1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		
		switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					c_map = new HashMap<>();
					c_map.put("control", "enable");
					control.child("control").updateChildren(c_map);
					c_map.clear();
					SketchwareUtil.showMessage(getApplicationContext(), "User App Active");
				}
				else {
					c_map = new HashMap<>();
					c_map.put("control", "disable");
					control.child("control").updateChildren(c_map);
					c_map.clear();
					SketchwareUtil.showMessage(getApplicationContext(), "User App Deactivate");
				}
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!edittext2.getText().toString().equals("")) {
					if (!edittext3.getText().toString().equals("")) {
						if (!edittext1.getText().toString().equals("")) {
							map = new HashMap<>();
							map.put("version", edittext2.getText().toString().trim());
							map.put("new", edittext3.getText().toString().trim());
							map.put("link", edittext1.getText().toString().trim());
							map.put("date", new SimpleDateFormat("dd-mm-yyyy").format(c.getTime()));
							update.child("app").updateChildren(map);
							map.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "Update sent");
							edittext1.setText("");
							edittext2.setText("");
							edittext3.setText("");
						}
						else {
							((EditText)edittext1).setError("Add App Link");
						}
					}
					else {
						((EditText)edittext3).setError("Add What’s New");
					}
				}
				else {
					((EditText)edittext2).setError("Add Version Code");
				}
			}
		});
		
		_update_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				update.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						mape = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								mape.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						textview4.setText("Recent version: ".concat(mape.get((int)0).get("version").toString()));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		update.addChildEventListener(_update_child_listener);
		
		_control_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("control").toString().equals("enable")) {
					switch1.setChecked(true);
					textview3.setText("Active");
				}
				if (_childValue.get("control").toString().equals("disable")) {
					switch1.setChecked(false);
					textview3.setText("Deactive");
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
		control.addChildEventListener(_control_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("Update Request");
		_edittext();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _edittext () {
		((LinearLayout)linear6).removeView(edittext2); 
		final com.google.android.material.textfield.TextInputLayout ex1 = new com.google.android.material.textfield.TextInputLayout (this);
		 ex1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		
		 ex1.setPadding((int)getDip(2), (int)getDip(2) , (int)getDip(2) , (int)getDip(2) );
		ex1.setGravity(Gravity.CENTER);
		ex1.setHintEnabled(true);
		ex1.setHint("Version Code");
		ex1.setBoxBackgroundMode(ex1.BOX_BACKGROUND_OUTLINE); 
		ex1.setBoxStrokeColor(0xFF2196F3);
		ex1.setBoxCornerRadii(5, 5, 5, 5);
		ex1.setErrorEnabled(true);
		ex1.setHintAnimationEnabled(true);
		
		ex1.addView(edittext2);
		 ((LinearLayout)linear6).addView(ex1);
		((LinearLayout)linear7).removeView(edittext3); 
		final com.google.android.material.textfield.TextInputLayout ex2 = new com.google.android.material.textfield.TextInputLayout (this);
		 ex2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		
		 ex2.setPadding((int)getDip(2), (int)getDip(2) , (int)getDip(2) , (int)getDip(2) );
		ex2.setGravity(Gravity.CENTER);
		ex2.setHintEnabled(true);
		ex2.setHint("What’s New");
		ex2.setBoxBackgroundMode(ex2.BOX_BACKGROUND_OUTLINE); 
		ex2.setBoxStrokeColor(0xFF2196F3);
		ex2.setBoxCornerRadii(5, 5, 5, 5);
		ex2.setErrorEnabled(true);
		ex2.setHintAnimationEnabled(true);
		
		ex2.addView(edittext3);
		 ((LinearLayout)linear7).addView(ex2);
		((LinearLayout)linear8).removeView(edittext1); 
		final com.google.android.material.textfield.TextInputLayout ex3 = new com.google.android.material.textfield.TextInputLayout (this);
		 ex3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		
		 ex3.setPadding((int)getDip(2), (int)getDip(2) , (int)getDip(2) , (int)getDip(2) );
		ex3.setGravity(Gravity.CENTER);
		ex3.setHintEnabled(true);
		ex3.setHint("New App Link");
		ex3.setBoxBackgroundMode(ex3.BOX_BACKGROUND_OUTLINE); 
		ex3.setBoxStrokeColor(0xFF2196F3);
		ex3.setBoxCornerRadii(5, 5, 5, 5);
		ex3.setErrorEnabled(true);
		ex3.setHintAnimationEnabled(true);
		
		ex3.addView(edittext1);
		 ((LinearLayout)linear8).addView(ex3);
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
