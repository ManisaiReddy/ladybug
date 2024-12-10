//package com.teamDroiders.ladybuddy;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.content.Intent;
//import android.location.Location;
//import android.os.Bundle;
//import android.speech.RecognitionListener;
//import android.speech.RecognizerIntent;
//import android.speech.SpeechRecognizer;
//import android.telephony.SmsManager;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.util.ArrayList;
//import java.util.Locale;
//
//public class EmergencyActivity extends AppCompatActivity {
//    private static final int PERMISSION_REQUEST_CODE = 1;
//    private SpeechRecognizer speechRecognizer;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_emergency);
//
//        // Request permissions
//        if (!checkPermissions()) {
//            requestPermissions();
//        }
//
//        // Initialize speech recognizer and location services
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        startListening();
//    }
//
//    private void startListening() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        speechRecognizer.setRecognitionListener(new RecognitionListener() {
//            @Override
//            public void onReadyForSpeech(Bundle params) {}
//
//            @Override
//            public void onBeginningOfSpeech() {}
//
//            @Override
//            public void onRmsChanged(float rmsdB) {}
//
//            @Override
//            public void onBufferReceived(byte[] buffer) {}
//
//            @Override
//            public void onEndOfSpeech() {}
//
//            @Override
//            public void onError(int error) {}
//
//            @Override
//            public void onResults(Bundle results) {
//                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                if (matches != null && matches.contains("danger")) {
//                    sendEmergencyMessage();
//                }
//            }
//
//            @Override
//            public void onPartialResults(Bundle partialResults) {}
//
//            @Override
//            public void onEvent(int eventType, Bundle params) {}
//        });
//
//        speechRecognizer.startListening(intent);
//    }
//
//    private void sendEmergencyMessage() {
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    String locationUrl = "https://www.google.com/maps/search/?api=1&query="
//                            + location.getLatitude() + "," + location.getLongitude();
//                    String message = "Emergency! I need help. My live location: " + locationUrl;
//
//                    // Add emergency contacts
//                    ArrayList<String> emergencyContacts = new ArrayList<>();
//                    emergencyContacts.add("1234567890"); // Replace with your contact
//                    emergencyContacts.add("0987654321");
//
//                    SmsManager smsManager = SmsManager.getDefault();
//                    for (String contact : emergencyContacts) {
//                        smsManager.sendTextMessage(contact, null, message, null, null);
//                    }
//
//                    Toast.makeText(EmergencyActivity.this, "Emergency message sent!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EmergencyActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private boolean checkPermissions() {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.SEND_SMS
//        }, PERMISSION_REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (!checkPermissions()) {
//                Toast.makeText(this, "Permissions are required for this feature.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}
package com.teamDroiders.ladybuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Locale;

public class EmergencyActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private SpeechRecognizer speechRecognizer;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // Request permissions
        if (!checkPermissions()) {
            requestPermissions();
        }

        // Initialize speech recognizer and location services
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Start listening for voice commands
        startListening();

        // Handle Emergency Call button click
        Button emergencyCallButton = findViewById(R.id.btn_emergency_call);
        emergencyCallButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:112")); // Replace "112" with your desired emergency number
            startActivity(intent);
        });
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {}

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && matches.contains("danger")) {
                    sendEmergencyMessage();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        speechRecognizer.startListening(intent);
    }

    private void sendEmergencyMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String locationUrl = "https://www.google.com/maps/search/?api=1&query="
                            + location.getLatitude() + "," + location.getLongitude();
                    String message = "Emergency! I need help. My live location: " + locationUrl;

                    // Add emergency contacts
                    ArrayList<String> emergencyContacts = new ArrayList<>();
                    emergencyContacts.add("6303481743"); // Replace with your contact
                    emergencyContacts.add("0987654321");

                    SmsManager smsManager = SmsManager.getDefault();
                    for (String contact : emergencyContacts) {
                        smsManager.sendTextMessage(contact, null, message, null, null);
                    }

                    Toast.makeText(EmergencyActivity.this, "Emergency message sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EmergencyActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS
        }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!checkPermissions()) {
                Toast.makeText(this, "Permissions are required for this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
