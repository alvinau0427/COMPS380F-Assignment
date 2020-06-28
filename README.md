# COMPS380F-Assignment
> OUHK 2019/20 Web Applications Design and Development (COMPS380F) Assignment

> JavaEE Web Application: Course Discussion Forum

[![Build Status](https://travis-ci.com/alvinau0427/COMPS380F-Assignment.svg?branch=master)](https://travis-ci.org/alvinau0427/COMPS380F-Assignment)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Getting Started
- The web application fulfil the following basic requirements on web pages and functionalities:
	- Using major techniques of **Java EE**, **HTML5**, **CSS**, **Javascript**.
	- Required to use **Spring MVC web framework** and **Spring Security**.
	- Dynamic pages generation based on user’s input or request.
	- Using the **Apache Derby database** as backend for data storage (but not necessary for uploaded files).
	- The web application should be easy to use for normal users.

## Fectures
- Forum hierarchy:
	- The forum has an index page, which shows 3 **categories**: Lecture, Lab, Other
	- Each category has a set of threads and the total number of threads.
	- Each thread has a post called **message topic**, a set of **replies** to that message topic, and the **total number of replies**.

- User registration and login

- Unregistered users can read all content except downloading file attachments and voting for the poll:
	- Read all threads in each of the categories.
	- View the result of the current forum poll, i.e., the number of users who voted for the poll, and the number of users who chose each MC response.

- Registered user can read all content, download file attachments, write new posts and vote for the poll:
	- Write a message topic of a new thread in one of the categories.
	- Write a new reply to one of the message topics.
	- Vote for the current forum poll; a user can only vote once for a poll.

- The forum administrator can do anything that a registered user can do, and the followings:
	- Delete a reply in a thread
	- Delete a thread (including the message topic and all its replies).
	- Edit (add, remove, update) the list of registered users and their information.
	- Create a new forum poll.

- Additional Features
	- History on the existing and previous forum poll results
	- Batch uploading of file attachments
	- Storing file attachments to the relational database

## Installation

## Setup
- Please create the JDBC derby database with the following information and import 3 of the SQL file in `src/resources`
```
Database name: GroupProject
Username: nbuser
Password: nbuser

Administrator permission user
username: admin, password: admin
username: keith, password: keithpw
usernmae: vanessa, password: vanessapw

User permission user
username: user1, password: user1
username: user2, password: user2
username: kevin, password: kevinpw
username: oliver, password: oliverpw
```

## Screenshots
![Image](https://github.com/alvinau0427/COMPS380F-Assignment/blob/master/doc/demo.png)

## License
- COMPS380F-Assignment is released under the [MIT License](https://opensource.org/licenses/MIT).
```
Copyright (c) 2020 alvinau0427

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
