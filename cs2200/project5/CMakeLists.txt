cmake_minimum_required(VERSION 3.6)
project(project5)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    answers.txt
    os-sim.c
    os-sim.h
    process.c
    process.h
    student.c
    student.h)

add_executable(project5 ${SOURCE_FILES})