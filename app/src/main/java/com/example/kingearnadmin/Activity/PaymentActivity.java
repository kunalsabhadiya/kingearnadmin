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
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.graphics.Typeface;
import java.text.DecimalFormat;


public class PaymentActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> m = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private AlertDialog.Builder d;
	private DatabaseReference history = _firebase.getReference("history");
	private ChildEventListener _history_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.payment);
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
		listview1 = (ListView) findViewById(R.id.listview1);
		d = new AlertDialog.Builder(this);
		
		_history_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				history.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						Collections.reverse(listmap);
						listview1.setAdapter(new Listview1Adapter(listmap));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
				history.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						Collections.reverse(listmap);
						listview1.setAdapter(new Listview1Adapter(listmap));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		history.addChildEventListener(_history_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("Pending Payment");
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
	
	
	public void _listrefresh () {
		listview1.setAdapter(new Listview1Adapter(listmap));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.cuspay, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView email = (TextView) _view.findViewById(R.id.email);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView number = (TextView) _view.findViewById(R.id.number);
			final TextView method = (TextView) _view.findViewById(R.id.method);
			final TextView amount = (TextView) _view.findViewById(R.id.amount);
			final TextView paypending = (TextView) _view.findViewById(R.id.paypending);
			final TextView date = (TextView) _view.findViewById(R.id.date);
			
			linear1.setVisibility(View.GONE);
			number.setTextIsSelectable(true);
			_rippleRoundStroke(linear1, "#FFFFFF", "#FFFFFF", 15, 0, "#FFFFFF");
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), Typeface.BOLD);
			email.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), Typeface.NORMAL);
			number.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semi.ttf"), Typeface.NORMAL);
			method.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semi.ttf"), Typeface.NORMAL);
			amount.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semi.ttf"), Typeface.NORMAL);
			paypending.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), Typeface.NORMAL);
			if (_data.get((int)_position).containsKey("key")) {
				if (_data.get((int)_position).get("subject").toString().contains("withdraw")) {
					linear1.setVisibility(View.VISIBLE);
					m = new HashMap<>();
					m.put("subject", "withdraw");
					m.put("seen", "true");
					history.child(_data.get((int)_position).get("key").toString()).updateChildren(m);
					m.clear();
					if (_data.get((int)_position).containsKey("name")) {
						name.setText(_data.get((int)_position).get("name").toString());
					}
					if (_data.get((int)_position).containsKey("email")) {
						email.setText(_data.get((int)_position).get("email").toString());
					}
					if (_data.get((int)_position).containsKey("acc")) {
						number.setText(_data.get((int)_position).get("acc").toString());
					}
					if (_data.get((int)_position).containsKey("method")) {
						method.setText(_data.get((int)_position).get("method").toString());
					}
					if (_data.get((int)_position).containsKey("amount")) {
						amount.setText("BDT. ".concat(new DecimalFormat("#,##,##,###.##").format(Double.parseDouble(_data.get((int)_position).get("amount").toString()))));
					}
					if (_data.get((int)_position).containsKey("date")) {
						date.setText(_data.get((int)_position).get("date").toString());
					}
					if (_data.get((int)_position).get("status").toString().contains("Pending")) {
						paypending.setTextColor(0xFFF44336);
						paypending.setText("Pending");
						paypending.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								d.setMessage("This payment has been paid?");
								d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										m = new HashMap<>();
										m.put("status", "Paid");
										history.child(_data.get((int)_position).get("key").toString()).updateChildren(m);
										m.clear();
										SketchwareUtil.showMessage(getApplicationContext(), "Thank You 😊");
									}
								});
								d.setNegativeButton("No", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										SketchwareUtil.showMessage(getApplicationContext(), "Pay as soon as possible");
									}
								});
								d.create().show();
							}
						});
					}
					else {
						if (_data.get((int)_position).get("status").toString().contains("Paid")) {
							paypending.setTextColor(0xFF4CAF50);
							paypending.setText("Paid");
						}
					}
				}
			}
			
			return _view;
		}
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
