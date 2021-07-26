package mooc.vandy.java4android.shapes.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mooc.vandy.java4android.shapes.R;
import mooc.vandy.java4android.shapes.logic.Logic;
import mooc.vandy.java4android.shapes.logic.LogicInterface;
import mooc.vandy.java4android.shapes.logic.Shapes;

import static android.content.Context.MODE_PRIVATE;


/**
 * MainActivity for this app.
 */
public class MainActivity
        extends AppCompatActivity
        implements OutputInterface {
    /**
     * String for LOGGING.
     */
    private final static String LOG_TAG =
            MainActivity.class.getCanonicalName();

    /**
     * Shared preference keys.
     */
    private final static String KEY_LENGTH = "length";
    private final static String KEY_WIDTH = "width";
    private final static String KEY_HEIGHT = "height";
    private final static String KEY_RADIUS = "radius";
    private final static String KEY_SHAPE = "shape";

    /**
     * Logic Instance.
     */
    private LogicInterface mLogic;

    /**
     * UI Components.
     */
    private TextView mOutput;

    /**
     * Edit Texts.
     */
    private EditText mLength;
    private EditText mWidth;
    private EditText mHeight;
    private EditText mRadius;

    /**
     * The Spinner (drop down selector) that you choose which shape to
     * use.
     */
    private Spinner mShapesSpinner;

    /**
     * Called when the activity is starting.
     * <p/>
     * Similar to 'main' in C/C++/Standalone Java
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // required
        super.onCreate(savedInstanceState);

        // create a new 'Logic' instance.
        mLogic = new Logic(this);

        // setup the UI.
        initializeUI();

        if (savedInstanceState != null) {
            mLength.setText(savedInstanceState.getString(KEY_LENGTH, "10"));
            mWidth.setText(savedInstanceState.getString(KEY_WIDTH, "10"));
            mHeight.setText(savedInstanceState.getString(KEY_HEIGHT, "10"));
            mRadius.setText(savedInstanceState.getString(KEY_RADIUS, "10"));
            mShapesSpinner.setSelection(savedInstanceState.getInt(KEY_SHAPE, 0));
        } else {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            mLength.setText(prefs.getString(KEY_LENGTH, "10"));
            mWidth.setText(prefs.getString(KEY_WIDTH, "10"));
            mHeight.setText(prefs.getString(KEY_HEIGHT, "10"));
            mRadius.setText(prefs.getString(KEY_RADIUS, "10"));
            mShapesSpinner.setSelection(prefs.getInt(KEY_SHAPE, 0));
        }
    }

    /**
     * Called to save the instance state before the activity is destroyed.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_LENGTH, mLength.getText().toString());
        outState.putString(KEY_WIDTH, mWidth.getText().toString());
        outState.putString(KEY_HEIGHT, mHeight.getText().toString());
        outState.putString(KEY_RADIUS, mRadius.getText().toString());
        outState.putInt(KEY_SHAPE, mShapesSpinner.getSelectedItemPosition());

        super.onSaveInstanceState(outState);
    }

    /**
     * Called when app is paused and is the recommended time to
     * save user input as shared preferences.
     */
    @Override
    protected void onPause() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(KEY_LENGTH, mLength.getText().toString());
        edit.putString(KEY_WIDTH, mWidth.getText().toString());
        edit.putString(KEY_HEIGHT, mHeight.getText().toString());
        edit.putString(KEY_RADIUS, mRadius.getText().toString());
        edit.putInt(KEY_SHAPE, mShapesSpinner.getSelectedItemPosition());
        edit.apply();

        super.onPause();
    }

    /**
     * This method sets up/gets reference to the UI components.
     */
    private void initializeUI() {
        // Set the layout.
        setContentView(R.layout.activity_main);

        // Store references to various views.
        mOutput = findViewById(R.id.outputET);
        mLength = findViewById(R.id.lengthET);
        mWidth = findViewById(R.id.widthET);
        mHeight = findViewById(R.id.heightET);
        mRadius = findViewById(R.id.radiusET);

        // Store a reference to the mShapesSpinner.
        mShapesSpinner = findViewById(R.id.spinner);

        // Initialize the adapter that is used to bind the data to the mMathSpinner widget.
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this,
                R.array.shapes,
                R.layout.spinner_item);
        mAdapter.setDropDownViewResource(R.layout.spinner_item);

        // Associate the ArrayAdapter with the Spinner.
        mShapesSpinner.setAdapter(mAdapter);
    }

    /**
     * Called when Button is Pressed.
     */
    public void buttonPressed(View buttonPress) {
        resetText();
        mLogic.process();
    }

    /**
     * Add the @a string to the EditText.
     */
    @SuppressLint("SetTextI18n")
    private void addToEditText(String string) {
        mOutput.setText("" + mOutput.getText() + string);
    }

    /**
     * Get the shape specified by the user.
     */
    @Override
    public Shapes getShape() {
        final String spinnerString =
                mShapesSpinner.getSelectedItem().toString();
        // .valueOf(String) is an automatically generated method of
        // all Enum(s).  It returns an instance of the enum if one
        // matches the string provided.
        return Shapes.valueOf(spinnerString);
    }

    /**
     * This method gets the value from an EditText object and converts it to a double.
     */
    private double getDoubleValueOfEditText(EditText editText) {
        // Preventative error check to make sure the passed in EditText is not null.
        if (editText != null) {
            Editable valueEditable = editText.getText();
            //  Preventative error check to make sure the Editable returned is not null.
            if (valueEditable != null) {
                String valueString = valueEditable.toString();
                // Error check to make sure the EditText is not empty.
                if (!valueString.isEmpty()) {
                    try {
                        // Convert the string value from the EditText to a double
                        double value = Double.parseDouble(valueString);
                        // return 0 if 'value' is below 0.
                        if (value > 0) {
                            return value;
                        } else {
                            return 0;
                        }
                    } catch (NumberFormatException ex) {
                        // Error logging if the EditText didn't contain a properly formatted double
                        Log.e(LOG_TAG, "NumberFormatException thrown when trying to " +
                                "convert to EditText's value to Double: " + ex);
                    }
                }
            }
        }
        // If for any reason the above checks failed, return a 0.
        return 0;
    }

    /**
     * Get the length.
     */
    @Override
    public double getLength() {
        return getDoubleValueOfEditText(mLength);
    }

    /**
     * Get the width.
     */
    @Override
    public double getWidth() {
        return getDoubleValueOfEditText(mWidth);
    }

    /**
     * Get the height.
     */
    @Override
    public double getHeight() {
        return getDoubleValueOfEditText(mHeight);
    }

    /**
     * Get the radius.
     */
    @Override
    public double getRadius() {
        return getDoubleValueOfEditText(mRadius);
    }

    /**
     * Reset the on-screen output (EditText box).
     */
    @Override
    public void resetText() {
        mOutput.setText("");
    }

    /**
     * Prints the string representation of the passed Java Object or primitive type.
     *
     * @param obj a String, int, double, float, boolean or any Java Object.
     */
    @Override
    public void print(Object obj) {
        String text = (obj != null ? obj.toString() : "null");
        addToEditText(text);
        String debug = text.replace("\n", "\\n");
        Log.d(LOG_TAG, "print(" + debug + ")");
    }
}
