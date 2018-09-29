/**
 * Copyright (c) 2018. [Zexin Zhong]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions an limitations under the License.
 */


package com.sep.UniTrips.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sep.UniTrips.model.User;

@Dao
public interface DataAccessObject {

    @Insert
    public void addUser(User user);

    /**
     * This query will return all the courses that in the database
     * @return all the courses that in the database
     */
    @Query("select * from users where username = :username")
    public User getUser(String username);

    @Query("select * from users where username = :username AND password = :password")
    public User checkPassword(String username, String password);

}
