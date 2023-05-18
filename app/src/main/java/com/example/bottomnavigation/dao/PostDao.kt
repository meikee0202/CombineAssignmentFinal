package com.example.bottomnavigation.dao

import androidx.room.*
import com.example.bottomnavigation.entities.Posts


//Data Access Object
//maps the methods in the interface to SQL queries
@Dao
interface PostDao {

    //selects all posts from the posts table and orders them by their ID in descending order.
    //returns a list of Posts objects.
    @Query("SELECT * FROM posts ORDER BY id DESC")
    suspend fun getAllPosts() : List<Posts>

    //this method selects a specific post from the posts table based on its ID
    //The id parameter is used in the SQL query to match against the ID column in the table
    //returns a single Posts object.
    @Query("SELECT * FROM posts WHERE id =:id")
    suspend fun getSpecificPost(id:Int) : Posts

    //@Insert tells Room to generate code that will insert the post object into the posts table
    //The onConflict parameter specifies what should happen if there is a conflict (i.e. if the primary key already exists).
    // the OnConflictStrategy.REPLACE value, if there is a conflict, the old row will be replaced with the new row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(post:Posts)

    //@Delete tells Room to generate code that will delete the post object from the posts table
    @Delete
    suspend fun deletePost(post:Posts)

    // This method deletes a specific post from the posts table based on its ID
    // The id parameter is used in the SQL query to match against the ID column in the table.
    @Query("DELETE FROM posts WHERE id =:id")
    suspend fun deleteSpecificPost(id:Int)

    //@Update tells Room to generate code that will update the post object in the posts table
    @Update
    suspend fun updatePost(post:Posts)
}