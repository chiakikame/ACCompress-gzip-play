import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

/*
 * Created by chiaki on 25/02/16.
 */
object Main {
    val size = 1048576

    @JvmStatic
    fun main(args: Array<String>) {
        // generate data
        val data = ByteArray(size)
        Random().nextBytes(data)

        // write those data in
        val writeTarget = ByteArrayOutputStream()
        val gzipCompressLayer = GzipCompressorOutputStream(writeTarget)
        gzipCompressLayer.write(data)
        gzipCompressLayer.finish()
        gzipCompressLayer.flush()

        // change the data so CRC check will fail
        val result = writeTarget.toByteArray()
        result[ size / 1024 ] = 30

        // time to decompress and see what happens
        val readSource = ByteArrayInputStream(result)
        val gzipDecompressLayer = GzipCompressorInputStream(readSource)
        try {
            val buffer = ByteArray(1024)
            while(gzipDecompressLayer.read(buffer) > 0) {

            }
        } catch (e: Exception) {
            println(e.javaClass.name)
            println(e.message)
        } finally {
            gzipDecompressLayer.close()
        }
    }
}