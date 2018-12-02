package life.hepi.hepipixpicdemo

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import life.hepi.hepipixpic.PixPic
import life.hepi.hepipixpic.adapter.image.impl.GlideAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                PixPic.with(this@MainActivity)
                    .setImageAdapter(GlideAdapter())
                    .setMaxCount(1)
                    .setMinCount(1)
                    .setIsEditorEnable(true)
                    .startPicker();
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 27) {
            val url = data?.getParcelableExtra<Uri>("IMAGE_URL")
            imageview.setImageURI(url);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
