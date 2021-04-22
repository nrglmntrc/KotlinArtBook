package com.nurgulmantarci.kotlinartbook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.database.getIntOrNull
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    val artNameList=ArrayList<String>()
    val artIdList=ArrayList<Int>()
    private lateinit var artAdapter: ArtListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artAdapter= ArtListAdapter(artNameList,artIdList)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=artAdapter

        getFromSQL()
    }

    fun getFromSQL(){
        try {

            if(activity!=null){
                val database=activity!!.openOrCreateDatabase("Arts",Context.MODE_PRIVATE,null)
                val cursor=database.rawQuery("SELECT * FROM arts",null)
                val artNameIx=cursor.getColumnIndex("artname")
                val idIx=cursor.getColumnIndex("id")

                artIdList.clear()
                artNameList.clear()

                while (cursor.moveToNext()){
                    artNameList.add(cursor.getString(artNameIx))
                    artIdList.add(cursor.getInt(idIx))
                }

                artAdapter.notifyDataSetChanged()

                cursor.close()
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}