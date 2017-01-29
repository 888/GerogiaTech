cmake_minimum_required(VERSION 3.6)
project(project3)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    traces/astar.trace
    traces/bzip2.trace
    traces/mcf.trace
    traces/perlbench.trace
    cachesim.c
    cachesim.h
    cachesim_driver.c
    Makefile
    project3.iml
    project3.pdf
    run_script.sh
    solution.log)

add_executable(project3 ${SOURCE_FILES})