from random import randrange
import time
import matplotlib.pyplot as plt

def matrix_multiply(A, B): 
    if len(A[0]) != len(B): 
        return None
    
    result = [[0 for _ in range(len(B[0]))] for _ in range(len(A))]
    
    for i in range(len(A)):
        for j in range(len(B[0])):
            for k in range(len(B)):
                result[i][j] += A[i][k] * B[k][j]
    
    return result

def matrix_generator(n):
    matrix = [[0 for _ in range(n)] for _ in range(n)]

    for i in range(n):
        for j in range(n):
            matrix[i][j] = randrange(10)
    
    return matrix

def matrix_add(A, B):
    if len(A) != len(B) or len(A[0]) != len(B[0]):
        return None
    
    rows = len(A)
    cols = len(A[0])
    
    result = []
    for _ in range(rows):
        row = [0] * cols
        result.append(row)
    
    for i in range(rows):
        for j in range(cols):
            result[i][j] = A[i][j] + B[i][j]
    
    return result

def measure_time(n, operation):
    A, B = matrix_generator(n), matrix_generator(n)
    start_time = time.perf_counter() 
    if operation == "add":
        matrix_add(A, B)
    elif operation == "multiply":
        matrix_multiply(A, B)
    end_time = time.perf_counter()
    return end_time - start_time


addition_sizes = [10, 100, 500, 1000, 2500, 5000, 7500, 10000]
multiplication_sizes = [10, 100, 500, 1000, 2500]
addition_times = []
multiplication_times = []

for n in addition_sizes:
    addition_times.append(measure_time(n, "add"))
    print(f"Size {n}: Addition Time {addition_times[-1]:.5f} s")

for n in multiplication_sizes:
    multiplication_times.append(measure_time(n, "multiply"))
    print(f"Size {n}: Multiplication Time {multiplication_times[-1]:.5f} s")


# Visualisierung
plt.plot(addition_sizes, addition_times, label="Addition (O(n^2))", marker="o")
plt.plot(multiplication_sizes, multiplication_times, label="Multiplication (O(n^3))", marker="o")
plt.xlabel("Matrix size n")
plt.ylabel("Time (s)")
plt.legend()
plt.show()