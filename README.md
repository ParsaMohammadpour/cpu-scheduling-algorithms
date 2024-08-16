# Scheduling algorithms<br/>
&emsp; Here as operating system assignment, we implemented following shceduling algorithms and then tested them with the test-cases that are provided in [here](https://github.com/ParsaMohammadpour/cpu-scheduling-algorithms/blob/master/TestCases.zip).<br/>
## implemented algorithms are as follows:<br>
### * Round-Robin:<br/>
&emsp; Storing incomming processes in a FIFO(First In First Out) and run the process until the time quantum ends, if the process hasn't been done, it will be moved to the end of the FIFO.
### * SJF:<br/>
&emsp; Each process that has the lower cpu burst time will be executed sooner. If two process has equal cpu-burst time, we use the FCFS (First Come, First Served) approach.
### * Preemtive-Priority:<br/>
&emsp; Every time that a new process comes, it will be added to the priority queue. The scheduler will only decide which process should be executed, when a process is done or terminated.
### * Non-Preemtive-Priority:<br/>
&emsp; Every time that a new process comes, it will be added to the priority queue. The scheduler will decide which process should be executed, when a new process with higher priority comes or the currently running process gets done or terminated.<br/>
<br>
## Input file structure:<br>
At the first line, the algorithm name is given. In the second line, we have number of total processes. And in other lines, for each process we have four numbers:<br/>
- process number
- process arrival time
- CPU-burst for that process
- process priority (low numbers mean higher priorities)
<br/>
## Output file structure:<br>
&emsp; First, we have to specify the algorithm name. In other lines, each line represents a cpu-assignment and has two values: first number shows time and the second number shows process number that has got the cpu in that time.
Then in the last line, we show average waiting time.
