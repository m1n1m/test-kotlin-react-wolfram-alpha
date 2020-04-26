package com.example.calc.service.impl.rest.wolfram

import com.example.calc.service.dto.CallWolframAlphaPlotImageResultDto
import com.example.calc.service.rest.wolfram.WolframAlphaFullResultsApiService
import com.wolfram.alpha.WAEngine
import com.wolfram.alpha.WAException
import com.wolfram.alpha.WAImage
import org.springframework.stereotype.Service


@Service
class WolframAlphaFullResultsApiServiceImpl : WolframAlphaFullResultsApiService {

    private val appID = "AW278K-K77H68RKL6"
    val engine: WAEngine = WAEngine()

    init {
        engine.appID = appID
    }

    override fun query(input: String): CallWolframAlphaPlotImageResultDto {

        engine.addFormat("image")
        val query = engine.createQuery()
        query.input = input

        val resultDto = CallWolframAlphaPlotImageResultDto()

        try {
            val queryResult = engine.performQuery(query)
            resultDto.success = !queryResult.isError && queryResult.isSuccess
            if (resultDto.success!!) {
                for (pod in queryResult.pods) {
                    if (pod.id == "Plot" && !pod.isError) {
                        val image = pod.subpods[0].contents[0] as WAImage
                        resultDto.plotImageUrl = image.url
                    }
                }
            }
        } catch (e: WAException) {
            e.printStackTrace()
        }

        return resultDto
    }
}