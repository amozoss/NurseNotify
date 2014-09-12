package willoughby.com.nursenotify;



import android.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by dan on 9/12/14.
 */
public class MainFragment extends Fragment {

  private EditText mPhoneTextView;
  private Button   mNotifyButton;
  private Button   mPanicButton;


  public MainFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    mPhoneTextView = (EditText)rootView.findViewById(R.id.main_phone_text_view);
    mNotifyButton = (Button)rootView.findViewById(R.id.main_notify_button);
    mNotifyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendSMSMessage("You have been summoned.");
      }
    });
    mPanicButton = (Button)rootView.findViewById(R.id.main_panic_button);
    mPanicButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendSMSMessage("Panic!! Your patient pressed the panic button!");
      }
    });
    return rootView;
  }


  protected void sendSMSMessage(String message) {
    boolean cancel = false;
    View focusView = null;
    Log.i("Send SMS", "");

    mPhoneTextView.setError(null);

    String phoneNo = mPhoneTextView.getText().toString();


    if (TextUtils.isEmpty(phoneNo)) {
      mPhoneTextView.setError(getString(R.string.error_field_required));
      focusView = mPhoneTextView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
      return;
    }

    try {
      SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(phoneNo, null, message, null, null);
      Toast.makeText(getActivity().getApplicationContext(), "Message sent.",
                     Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(getActivity().getApplicationContext(),
                     "SMS faild, please try again.",
                     Toast.LENGTH_LONG).show();
      e.printStackTrace();
    }
  }

}
