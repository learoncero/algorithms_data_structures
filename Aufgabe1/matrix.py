from random import randrange
import time

def matrix_multiply(A, B): 
    if len(A[0]) != len(B): 
        return None
    
    result = [[0 for _ in range(len(B[0]))] for _ in range(len(A))]
    
    for i in range(len(A)):
        for j in range(len(B[0])):
            for k in range(len(B)):
                result[i][j] += A[i][k] * B[k][j]
    
    return result

def matrix_generator(rows, columns):
    matrix = [[0 for _ in range(columns)] for _ in range(rows)]

    for i in range(rows):
        for j in range(columns):
            matrix[i][j] = randrange(10)
    
    return matrix

def matrix_add(A, B):
    if len(A) != len(B) or len(A[0]) != len(B[0]):
        return None
    
    result = [[0 for _ in range(len(A[0]))] for _ in range(len(A))]

    for i in range(len(A)):
        for j in range(len(A[0])):
            result[i][j] = A[i][j] + B[i][j]
    
    return result

def measure_time(rows, cols, operation):
    A, B = matrix_generator(rows, cols), matrix_generator(rows, cols)
    start_time = time.perf_counter()
    if operation == "add":
        matrix_add(A, B)
    elif operation == "multiply":
        matrix_multiply(A, B)
    end_time = time.perf_counter()
    return end_time - start_time


# sizes = [10, 100, 500, 1000, 5000, 10000]
sizes = [10, 50, 100, 200]
addition_times = []
multiplication_times = []

for n in sizes:
    addition_times.append(measure_time(n, "add"))
    multiplication_times.append(measure_time(n, "multiply"))
    print(f"Size {n}: Addition {addition_times[-1]:.5f} s, Multiplication {multiplication_times[-1]:.5f} s")