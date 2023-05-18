package com.example.bottomnavigation.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity, define database table call posts
@Entity(tableName = "Posts")
class Posts: Serializable {

    //autoGenerate parameter, automatically generate a unique id for each row that is inserted into the table.
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    @ColumnInfo(name = "title")
    var title:String? = null

    @ColumnInfo(name = "date")
    var date:String? = null

    @ColumnInfo(name = "post_text")
    var postText:String? = null

    @ColumnInfo(name = "img_path")
    var imgPath:String? = null



    //return a string representation of a Posts object
    override fun toString(): String {

        //the string is formatted as "title : dateTime".
        return "$title : $date"

    }
}