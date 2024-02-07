package com.example.kingearnadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.kingearnadmin.R;
import com.example.kingearnadmin.Utils.SketchwareUtil;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;


public class UserActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> m = new HashMap<>();
	private HashMap<String, Object> m2 = new HashMap<>();
	private String silverad = "";
	private String goldad = "";
	private String diamondad = "";
	
	private ArrayList<String> spin = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private AlertDialog.Builder d1;
	private AlertDialog.Builder d2;
	private DatabaseReference membership = _firebase.getReference("membership");
	private ChildEventListener _membership_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.user);
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
		d1 = new AlertDialog.Builder(this);
		d2 = new AlertDialog.Builder(this);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				d1.setMessage("Want To Give The User A Subscription?");
				d1.setPositiveButton("Silver", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (listmap.get((int)_position).get("Silver").toString().equals("false")) {
							m = new HashMap<>();
							m.put("Silver", "true");
							m.put("Gold", "false");
							m.put("Diamond", "false");
							m.put("Free", "false");
							m.put("limit", silverad);
							users.child(listmap.get((int)_position).get("uid").toString()).updateChildren(m);
							m.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "Silver Added");
							_list();
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "This user already Sliver Member");
						}
					}
				});
				d1.setNeutralButton("Gold", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (listmap.get((int)_position).get("Gold").toString().equals("false")) {
							m = new HashMap<>();
							m.put("Silver", "false");
							m.put("Gold", "true");
							m.put("Diamond", "false");
							m.put("Free", "false");
							m.put("limit", goldad);
							users.child(listmap.get((int)_position).get("uid").toString()).updateChildren(m);
							m.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "Gold Added");
							_list();
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "This user already Gold Member");
						}
					}
				});
				d1.setNegativeButton("Diamond", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (listmap.get((int)_position).get("Diamond").toString().equals("false")) {
							m = new HashMap<>();
							m.put("Silver", "false");
							m.put("Gold", "false");
							m.put("Diamond", "true");
							m.put("Free", "false");
							m.put("limit", diamondad);
							users.child(listmap.get((int)_position).get("uid").toString()).updateChildren(m);
							m.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "Diamond Added");
							_list();
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "This user already Diamond Member");
						}
					}
				});
				d1.create().show();
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				d2.setMessage("You Can Block, Unblock This User");
				if (listmap.get((int)_position).get("block").toString().equals("false")) {
					d2.setPositiveButton("Block", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							m2 = new HashMap<>();
							m2.put("block", "true");
							users.child(listmap.get((int)_position).get("uid").toString()).updateChildren(m2);
							m2.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "User Blocked 🚫");
						}
					});
				}
				if (listmap.get((int)_position).get("block").toString().equals("true")) {
					d2.setNegativeButton("Unblock", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							m2 = new HashMap<>();
							m2.put("block", "false");
							users.child(listmap.get((int)_position).get("uid").toString()).updateChildren(m2);
							m2.clear();
							SketchwareUtil.showMessage(getApplicationContext(), "User Unblocked ✅");
						}
					});
				}
				d2.create().show();
				return true;
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				users.addListenerForSingleValueEvent(new ValueEventListener() {
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
						_loadingdialog(false, "");
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
				users.addListenerForSingleValueEvent(new ValueEventListener() {
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
						_loadingdialog(false, "");
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
		users.addChildEventListener(_users_child_listener);
		
		_membership_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("membership").toString().equals("Silver")) {
					if (_childValue.containsKey("ad")) {
						silverad = _childValue.get("ad").toString();
					}
				}
				if (_childValue.get("membership").toString().equals("Gold")) {
					if (_childValue.containsKey("ad")) {
						goldad = _childValue.get("ad").toString();
					}
				}
				if (_childValue.get("membership").toString().equals("Diamond")) {
					if (_childValue.containsKey("ad")) {
						diamondad = _childValue.get("ad").toString();
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
		setTitle("Total Users");
		_loadingdialog(true, "Loading...");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
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
	
	
	public void _list () {
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
				_view = _inflater.inflate(R.layout.users, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			
			_rippleRoundStroke(linear1, "#FFFFFF", "#FFFFFF", 15, 0, "#FFFFFF");
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsans_bold.ttf"), 0);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_sans.ttf"), 0);
			if (_data.get((int)_position).containsKey("uid")) {
				if (_data.get((int)_position).containsKey("firstname") && _data.get((int)_position).containsKey("lastname")) {
					textview1.setText(_data.get((int)_position).get("firstname").toString().concat(" ".concat(_data.get((int)_position).get("lastname").toString())));
				}
				if (!_data.get((int)_position).get("avatar").toString().equals("null")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("avatar").toString())).into(circleimageview1);
				}
				if (_data.get((int)_position).containsKey("email")) {
					textview2.setText(_data.get((int)_position).get("email").toString());
				}
				if (_data.get((int)_position).get("Free").toString().equals("true")) {
					imageview1.setVisibility(View.GONE);
				}
				else {
					if (_data.get((int)_position).get("Silver").toString().equals("true")) {
						imageview1.setImageResource(R.drawable.logo_2);
						imageview1.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								SketchwareUtil.showMessage(getApplicationContext(), "Silver Member");
							}
						});
					}
					else {
						if (_data.get((int)_position).get("Gold").toString().equals("true")) {
							imageview1.setImageResource(R.drawable.logo_1);
							imageview1.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									SketchwareUtil.showMessage(getApplicationContext(), "Gold Member");
								}
							});
						}
						else {
							if (_data.get((int)_position).get("Diamond").toString().equals("true")) {
								imageview1.setImageResource(R.drawable.logo_3);
								imageview1.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View _view) {
										SketchwareUtil.showMessage(getApplicationContext(), "Diamond Member");
									}
								});
							}
							else {
								if (_data.get((int)_position).get("block").toString().equals("true")) {
									imageview1.setImageResource(R.drawable.logo_4);
									imageview1.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View _view) {
											SketchwareUtil.showMessage(getApplicationContext(), "Member Blocked");
										}
									});
								}
							}
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
