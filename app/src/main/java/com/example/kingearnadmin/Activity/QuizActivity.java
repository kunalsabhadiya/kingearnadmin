package com.example.kingearnadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.BaseAdapter;

import com.example.kingearnadmin.R;
import com.example.kingearnadmin.Utils.SketchwareUtil;
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
import android.view.View;


public class QuizActivity extends AppCompatActivity {
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String spinner = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String key = "";
	
	private ArrayList<String> spin = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
	
	private LinearLayout linear1;
	private EditText edittext1;
	private LinearLayout linear2;
	private Spinner spinner1;
	private Button button1;
	private ListView listview1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private EditText edittext2;
	private EditText edittext4;
	private EditText edittext3;
	private EditText edittext5;
	
	private DatabaseReference quiz = _firebase.getReference("quiz");
	private ChildEventListener _quiz_child_listener;
	private AlertDialog.Builder d;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.quiz);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		button1 = (Button) findViewById(R.id.button1);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		edittext5 = (EditText) findViewById(R.id.edittext5);
		d = new AlertDialog.Builder(this);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 1) {
					spinner = "a";
				}
				if (_position == 2) {
					spinner = "b";
				}
				if (_position == 3) {
					spinner = "c";
				}
				if (_position == 4) {
					spinner = "d";
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Enter Question");
				}
				else {
					if (edittext2.getText().toString().equals("")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Enter Option A");
					}
					else {
						if (edittext3.getText().toString().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Enter Option B");
						}
						else {
							if (edittext4.getText().toString().equals("")) {
								SketchwareUtil.showMessage(getApplicationContext(), "Enter Option C");
							}
							else {
								if (edittext5.getText().toString().equals("")) {
									SketchwareUtil.showMessage(getApplicationContext(), "Enter Option D");
								}
								else {
									if (spinner.equals("")) {
										SketchwareUtil.showMessage(getApplicationContext(), "Select Answer");
									}
									else {
										key = quiz.push().getKey();
										map = new HashMap<>();
										map.put("ques", edittext1.getText().toString());
										map.put("a", edittext2.getText().toString());
										map.put("b", edittext3.getText().toString());
										map.put("c", edittext4.getText().toString());
										map.put("d", edittext5.getText().toString());
										map.put("ans", spinner);
										map.put("key", key);
										quiz.child(key).updateChildren(map);
										map.clear();
										edittext1.setText("");
										edittext2.setText("");
										edittext4.setText("");
										edittext3.setText("");
										edittext5.setText("");
										spinner = "";
										SketchwareUtil.showMessage(getApplicationContext(), "Done");
									}
								}
							}
						}
					}
				}
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				d.setTitle("Delete This Quiz?");
				d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						quiz.child(lm.get((int)_position).get("key").toString()).removeValue();
						lm.remove((int)(_position));
						SketchwareUtil.showMessage(getApplicationContext(), "Deleted");
					}
				});
				d.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
				return true;
			}
		});
		
		_quiz_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				quiz.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lm = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lm.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(lm));
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
				quiz.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lm = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lm.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(lm));
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
		quiz.addChildEventListener(_quiz_child_listener);
	}
	
	private void initializeLogic() {
		spin.add("Select Answer");
		spin.add("A");
		spin.add("B");
		spin.add("C");
		spin.add("D");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spin));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
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
				_view = _inflater.inflate(R.layout.cus, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final CheckBox checkbox1 = (CheckBox) _view.findViewById(R.id.checkbox1);
			final CheckBox checkbox2 = (CheckBox) _view.findViewById(R.id.checkbox2);
			final CheckBox checkbox3 = (CheckBox) _view.findViewById(R.id.checkbox3);
			final CheckBox checkbox4 = (CheckBox) _view.findViewById(R.id.checkbox4);
			
			linear1.setVisibility(View.GONE);
			if (_data.get((int)_position).containsKey("key")) {
				linear1.setVisibility(View.VISIBLE);
				if (_data.get((int)_position).containsKey("ques")) {
					textview1.setText("* ".concat(_data.get((int)_position).get("ques").toString()));
				}
				if (_data.get((int)_position).containsKey("a")) {
					checkbox1.setText(_data.get((int)_position).get("a").toString());
				}
				if (_data.get((int)_position).containsKey("b")) {
					checkbox2.setText(_data.get((int)_position).get("b").toString());
				}
				if (_data.get((int)_position).containsKey("c")) {
					checkbox3.setText(_data.get((int)_position).get("c").toString());
				}
				if (_data.get((int)_position).containsKey("d")) {
					checkbox4.setText(_data.get((int)_position).get("d").toString());
				}
				if (true) {
					checkbox1.setChecked(true);
					checkbox1.setChecked(false);
					checkbox1.setChecked(false);
					checkbox1.setChecked(false);
				}
				else {
					if (true) {
						checkbox1.setChecked(false);
						checkbox1.setChecked(true);
						checkbox1.setChecked(false);
						checkbox1.setChecked(false);
					}
					else {
						if (true) {
							checkbox1.setChecked(false);
							checkbox1.setChecked(false);
							checkbox1.setChecked(true);
							checkbox1.setChecked(false);
						}
						else {
							if (true) {
								checkbox1.setChecked(false);
								checkbox1.setChecked(false);
								checkbox1.setChecked(false);
								checkbox1.setChecked(true);
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
