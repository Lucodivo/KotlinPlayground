import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

/*
Performance test based on...
Leetcode Problem 2089: Find Target Indices After Sorting Array
You are given a 0-indexed integer array nums and a target element target.
A target index is an index i such that nums[i] == target.
Return a list of the target indices of nums after sorting nums in non-decreasing order.
If there are no target indices, return an empty list. The returned list must be sorted in increasing order.
 */

// NOTE: Jave decompiled code is fromKotlin 1.6.21

class Performance_TwoCountsOneList {
    /* Decompiled Java Byte Code
    - asserts that list isn't null
    - { unique per function way of finding count and starting index }
    - Result is created as an ArrayList of specified size, entries are entered through a for loop,
    mostly as expected but with some odd unused loop variables set to false

   @NotNull
   public final List targetIndices_xxxXxxx(@NotNull int[] nums, int target) {
      Intrinsics.checkNotNullParameter(nums, "nums");

      // { unique per function way of finding count and starting index }

      boolean var16 = false;
      ArrayList var17 = new ArrayList(count);
      boolean var18 = false;
      boolean var19 = false;
      var8 = 0;

      for(int var9 = count; var8 < var9; ++var8) {
         boolean var11 = false;
         int var13 = false;
         Integer var15 = startingIndex + var8;
         var17.add(var15);
      }

      return (List)var17;
   }

     */


    fun targetIndices_forLoop(nums: IntArray, target: Int): List<Int> {
        var count = 0
        var startingIndex = 0
        for(item in nums) {
            if(item < target) { startingIndex ++ }
            else if(item == target) { count++ }
        }
        return List(count) {index -> startingIndex + index}
    }

    /* Decompiled Java Byte Code

    Decompiled Java creates a straight forward for loop, near line for line what you'd expect for calculating
    the starting index and count.

   @NotNull
   public final List targetIndices_forLoop(@NotNull int[] nums, int target) {
      // ...

      int count = 0;
      int startingIndex = 0;
      int[] var7 = nums;
      int var8 = nums.length;

      for(int var6 = 0; var6 < var8; ++var6) {
         int item = var7[var6];
         if (item < target) {
            ++startingIndex;
         } else if (item == target) {
            ++count;
         }
      }

      // ...
   }
   */

    fun targetIndices_forEach(nums: IntArray, target: Int): List<Int> {
        var count = 0
        var startingIndex = 0
        nums.forEach { num ->
            if(num < target) { startingIndex++ }
            else if(num == target) { count++ }
        }
        return List(count) {index -> startingIndex + index}
    }

    /* Decompiled Java Byte Code

    Decompiled Java creates a straight forward for loop, near line for line what you'd expect for calculating
    the starting index and count. HOWEVER, there is an unused loop integer set to false every loop iteration.

   @NotNull
   public final List targetIndices_forEach(@NotNull int[] nums, int target) {
      // ...

      int count = 0;
      int startingIndex = 0;
      int[] $this$forEach$iv = nums;
      int $i$f$forEach = false;
      int var7 = 0;

      int element$iv;
      for(int var8 = nums.length; var7 < var8; ++var7) {
         element$iv = $this$forEach$iv[var7];
         int var11 = false;
         if (element$iv < target) {
            ++startingIndex;
         } else if (element$iv == target) {
            ++count;
         }
      }

      // ...
   }
   */

    fun targetIndices_doubleCount(nums: IntArray, target: Int): List<Int> {
        val count = nums.count { it == target }
        val startingIndex = nums.count { it < target }
        return List(count) {index -> startingIndex + index}
    }

    /* Decompiled Java Byte Code

    Decompiled Java creates two straight forward for loops that use variables outside of the loop to track count and startingIndex.
    HOWEVER, there are once again funky unused loop variables set or instantiated to false each and every loop iteration.

   @NotNull
   public final List targetIndices_doubleCount(@NotNull int[] nums, int target) {
      // ...

      int[] $this$count$iv = nums;
      int $i$f$count = false;
      int count$iv = 0;
      int count$iv = 0;

      int var8;
      int element$iv;
      boolean var11;
      for(var8 = nums.length; count$iv < var8; ++count$iv) {
         element$iv = $this$count$iv[count$iv];
         var11 = false;
         if (element$iv == target) {
            ++count$iv;
         }
      }

      int[] $this$count$iv = nums;
      int $i$f$count = false;
      count$iv = 0;
      var8 = 0;

      for(element$iv = nums.length; var8 < element$iv; ++var8) {
         int element$iv = $this$count$iv[var8];
         int var12 = false;
         if (element$iv < target) {
            ++count$iv;
         }
      }

      int startingIndex = count$iv;

      // ...
   }
   */

    fun targetIndices_fold(nums: IntArray, target: Int): List<Int> {
        val (count, startingIndex) = nums.fold(Pair(0, 0)){ acc, item ->
            Pair(
                if(item == target) acc.first.inc() else acc.first,
                if(item < target) acc.second.inc() else acc.second
            )
        }
        return List(count) { index -> startingIndex + index }
    }

    /* Decompiled Java Byte Code

    Decompiled Java creates a fairly straight forward for loop that allocates a new Pair object for every iteration which
    replaces the outside Pair accumulator reference.
    HOWEVER, there is once again an unused loop integer instantiated to false each and every loop iteration.
    Allocation of Pair object every loop iteration is most likely the biggest bottleneck of this algorithm.

    @NotNull
    public final List targetIndices_fold(@NotNull int[] nums, int target) {
       // ...

       int[] $this$fold$iv = nums;
       Object initial$iv = new Pair(0, 0);
       int $i$f$fold = false;
       Object accumulator$iv = initial$iv;
       int var10 = 0;

       for(int var11 = nums.length; var10 < var11; ++var10) {
          int element$iv = $this$fold$iv[var10];
          int var15 = false;
          accumulator$iv = new Pair(element$iv == target ? ((Number)accumulator$iv.getFirst()).intValue() + 1 : ((Number)accumulator$iv.getFirst()).intValue(), element$iv < target ? ((Number)accumulator$iv.getSecond()).intValue() + 1 : ((Number)accumulator$iv.getSecond()).intValue());
       }

       int count = ((Number)accumulator$iv.component1()).intValue();
       int startingIndex = ((Number)accumulator$iv.component2()).intValue();

      // ...
    }
    */

    /*
    Performance results after running each version 1,000,000 times:
        for loop: ~64ms
        for each: ~88ms
        double count: ~108ms
        fold: ~269ms
     */
    @Test
    fun twoAccumulatorsOneList_Performance() {
        val nums = intArrayOf(1,2,5,2,3,7,8,9,9,6,9,9,9,6,9,9,0,0,0,0,2,2,2,3,4,5,3,2,12,6,2,32,32,3,1,3,0)
        val target = 6
        val expectedResult = listOf(22,23,24)

        val repeatCount = 1_000_000

        var forLoopResult: List<Int> = emptyList()
        val forLoopMS = measureTimeMillis { // ~64ms
            repeat(repeatCount) {
                forLoopResult = targetIndices_forLoop(nums, target)
            }
        }

        var forEachResult: List<Int> = emptyList()
        val forEachMS = measureTimeMillis { // ~88ms
            repeat(repeatCount) {
                forEachResult = targetIndices_forEach(nums, target)
            }
        }

        var doubleCountResult: List<Int> = emptyList()
        val doubleCountMS = measureTimeMillis { // ~108ms
            repeat(repeatCount) {
                doubleCountResult = targetIndices_doubleCount(nums, target)
            }
        }

        var foldResult: List<Int> = emptyList()
        val foldMS = measureTimeMillis { // ~269ms
            repeat(repeatCount) {
                foldResult = targetIndices_fold(nums, target)
            }
        }

        println("""
            ==== target indices ====
            For Loop: $forLoopMS ms
            For Each: $forEachMS ms
            Double Count: $doubleCountMS ms
            Fold: $foldMS ms
        """.trimIndent())

        assertEquals(expectedResult, forLoopResult)
        assertEquals(expectedResult, forEachResult)
        assertEquals(expectedResult, doubleCountResult)
        assertEquals(expectedResult, foldResult)
    }
}