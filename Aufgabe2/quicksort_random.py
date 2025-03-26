import random
import array as arr

def quicksort_random(list, start, end):
    comparisons_total = 0
    swaps_total = 0

    if start < end:
        pivot_index, comparisons, swaps = partition_random(list, start, end)
        comparisons_total += comparisons
        swaps_total += swaps
        
        left_comparisons, left_swaps = quicksort_random(list, start, pivot_index - 1)
        comparisons_total += left_comparisons
        swaps_total += left_swaps
        
        right_comparisons, right_swaps = quicksort_random(list, pivot_index + 1, end)
        comparisons_total += right_comparisons
        swaps_total += right_swaps

    return comparisons_total, swaps_total


def partition_random(list, start, end):
    pivot_index = random.randint(start, end)
    list[pivot_index], list[end] = list[end], list[pivot_index]
    pivot_element = list[end]
    i = start - 1

    comparisons = 0
    swaps = 0

    for j in range(start, end):
        comparisons += 1
        if list[j] < pivot_element: # or <=
            i += 1
            list[i], list[j] = list[j], list[i] #swap
            swaps += 1

    list[i + 1], list[end] = list[end], list[i + 1] #swap
    swaps += 1

    return i + 1, comparisons, swaps


if __name__ == "__main__":
    test_cases = {
        "list1": [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5],
        "list2": [1, 2, 3, 4, 5, 6, 7, 8, 9],
        "list3": [9, 8, 7, 6, 5, 4, 3, 2, 1],
        "list4": [1, 1, 1, 1, 1, 1, 1, 1, 1],
        "list5": [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
    }

    for name, test_list in test_cases.items():
        comparisons, swaps = quicksort_random(test_list, 0, len(test_list) - 1)
        print(f"{name}: {test_list}")
        print(f"Comparisons {name}: {comparisons}")
        print(f"Swaps {name}: {swaps}\n")

    # Testing with array.array
    arr1 = arr.array('i', [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5])
    comparisons, swaps = quicksort_random(arr1, 0, len(arr1) - 1)
    print("arr1:", arr1.tolist())
    print("Comparisons arr1:", comparisons)
    print("Swaps arr1:", swaps)