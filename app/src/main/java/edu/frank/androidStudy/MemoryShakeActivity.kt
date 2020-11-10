package edu.frank.androidStudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.frank.androidStudy.databinding.ActivityMemoryShakeBinding
import edu.frank.androidStudy.extension.setBindingLayout
import timber.log.Timber
import java.util.*

class MemoryShakeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setBindingLayout<ActivityMemoryShakeBinding>(
            this,
            R.layout.activity_memory_shake
        )
        binding.btShake.setOnClickListener {
            shake()
        }

        binding.btNotShake.setOnClickListener {
            notShake()
        }
    }

    /**
     * 　排序后打印二维数组，一行行打印
     */
    private fun shake() {
        val dimension = 300
        val lotsOfInts = Array(dimension) {
            IntArray(
                dimension
            )
        }
        val randomGenerator = Random()
        for (i in lotsOfInts.indices) {
            for (j in lotsOfInts[i].indices) {
                lotsOfInts[i][j] = randomGenerator.nextInt()
            }
        }
        for (i in lotsOfInts.indices) {
            var rowAsStr = ""
            //排序
            val sorted: IntArray = getSorted(lotsOfInts[i])
            //拼接打印
            for (j in lotsOfInts[i].indices) {
                rowAsStr += sorted[j]
                if (j < lotsOfInts[i].size - 1) {
                    rowAsStr += ", "
                }
            }
        }
    }

    /**
     * 　排序后打印二维数组，一行行打印
     */
    fun notShake() {
        val dimension = 300
        val lotsOfInts = Array(dimension) {
            IntArray(
                dimension
            )
        }
        val randomGenerator = Random()
        for (i in lotsOfInts.indices) {
            for (j in 0 until lotsOfInts[i].size) {
                lotsOfInts[i][j] = randomGenerator.nextInt()
            }
        }

        //优化以后
        val sb = StringBuilder()
        var rowAsStr = ""
        for (i in lotsOfInts.indices) {
            //清除上一行
            sb.delete(0, rowAsStr.length)
            //排序
            val sorted = getSorted(lotsOfInts[i])
            //拼接打印
            for (j in 0 until lotsOfInts[i].size) {
//                rowAsStr += sorted[j];
                sb.append(sorted[j])
                if (j < lotsOfInts[i].size - 1) {
//                    rowAsStr += ", ";
                    sb.append(", ")
                }
            }
            rowAsStr = sb.toString()
            Timber.i( "Row $i: $rowAsStr")
        }
    }

    private fun getSorted(input: IntArray): IntArray {
        val clone = input.clone()
        Arrays.sort(clone)
        return clone
    }
}