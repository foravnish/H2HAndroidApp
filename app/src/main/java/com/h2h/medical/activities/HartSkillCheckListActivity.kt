package com.h2h.medical.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.h2h.medical.R
import com.h2h.medical.helper.HitApiHelper
import com.h2h.medical.utils.ApiResponse
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.AppUtils
import com.h2h.medical.utils.Constants
import com.h2h.medical.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_check_list.btn_save
import kotlinx.android.synthetic.main.activity_check_list.tv_header_title
import kotlinx.android.synthetic.main.age_spacefic.*
import kotlinx.android.synthetic.main.care_of.*
import kotlinx.android.synthetic.main.careofpatient.*
import kotlinx.android.synthetic.main.careofpatient2.*
import kotlinx.android.synthetic.main.endocrine.*
import kotlinx.android.synthetic.main.equpment.*
import kotlinx.android.synthetic.main.general_skil.*
import kotlinx.android.synthetic.main.gestroin.*
import kotlinx.android.synthetic.main.neurology.*
import kotlinx.android.synthetic.main.pediatrics.*
import kotlinx.android.synthetic.main.psychai.*
import kotlinx.android.synthetic.main.pulmonary.*
import kotlinx.android.synthetic.main.renal.*
import kotlinx.android.synthetic.main.wound.*
import org.json.JSONObject


class HartSkillCheckListActivity : BaseActivity() , HitApiHelper {


    var rdStandardStr=""
    var rdIsolationStr=""
    var rdTpnStr=""
    var rdAdultStr=""
    var rdDefibrillatorsStr=""
    var rdUnderstandingStr=""
    var rdPatientStr=""
    var rdPatientHeadStr=""
    var rdDocumentStr=""
    var rdSafeStr=""
    var rdPediatricGStr =""
    var  rdGrowthDevStr=""
    var rdFamilyStr =""
    var rdSuctionStr =""
    var rdCalculationsofStr =""
    var  rdVentilatorStr=""
    var  rdTracheostomyStr=""
    var  tdCoughAStr=""
    var rdPediatricPStr =""
    var rdBronchoStr =""
    var  rdCysticStr=""
    var  rdHypotensionStr=""
    var  rdCongestiveStr=""
    var  rdFluidRetStr=""
    var  rdHypertensionStr=""
    var  rdAntibioticsStr=""
    var  rdAnticoagulantsStr=""
    var  rdAntiplateletStr=""
    var rdDiureticsStr =""
    var  rdAuscultationStr=""
    var rdPulseStr =""
    var  rdAdministrationStr=""
    var rdWorkofStr =""
    var  rdRespiratoryAssStr=""
    var rdRespiratoryStr =""
    var rdSpinaStr =""
    var rdPainScaleStr =""
    var  rdResponseToStr=""
    var  rdFallStr=""
    var  rdNationalStr=""
    var  rdSafetyStr=""
    var  rdInfantStr=""
    var  rdToddlerStr=""
    var rdPreschoolerStr =""
    var  rdSchoolAgeStr=""
    var rdAdolescentsStr =""
    var  rdYoungStr=""
    var rdApicalStr =""
    var rdPeripheralStr =""
    var  rdCardiacStr=""
    var  rdChestStr =""
    var  rdAirwayManagementStr =""
    var  rdVentilatorManagementStr =""
    var rdAsthmaStr  =""
    var   rdChronicStr=""
    var  rdPrimaryPulmonaryStr =""
    var  rdPulmonaryStr =""
    var rdBronchodilatorsStr  =""
    var  rdInhalersStr =""
    var  rdNeurologicalStr =""
    var  rdNeuroMotorStr =""
    var  rdSeizureStr =""
    var   rdParaplegiaStr=""
    var  rdCorticosteroidsStr =""
    var rdCirculationStr  =""
    var  rdGaitStr =""
    var   rdRangeofMotionStr=""
    var   rdRangeofMotionAStr=""
    var  rdBraceStr =""
    var  rdCrutchStr =""
    var  rdAssistiveStr =""
    var  rdWheelchairsStr =""
    var rdBowelStr=""

    var  rdFluidStr=""
    var rdNutritionalStr =""
    var  rdEquipmentStr=""
    var  rdColostomyStr=""
    var  rdPEGGastronomyStr=""
    var  rdDrainageStr=""
    var  rdTubeFeedingStr=""
    var  rdGastrointestinalStr=""
    var rdArterioStr =""
    var rdFluidBalance2Str =""
    var  rdOutputweightStr=""
    var  rdStraightStr=""
    var rdSelfCatheterizationStr =""
    var  rdIleostomyStr=""
    var  rdIrrigationsStr=""
    var rdSuprapubicStr =""
    var  rdShuntsandStr =""
    var rdUrinaryStr =""
    var  rdHyperandStr=""
    var  rdDiabetesAndStr=""
    var rdGlucometersStr =""
    var  rdInsulinPumpsStr=""
    var rdInsulinAdministrationStr =""
    var  rdSkinAssessmentStr=""
    var  rdBradenStr=""
    var  rdCareofPressureStr=""
    var  rdDryandWetStr=""
    var rdPositioningStr =""
    var rdSpecialMattressesStr =""
    var  rdCognitiveStr=""
    var rdMoodStr = ""

    private lateinit var mAppPreferences: AppPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        tv_header_title.setText("Hand 2 Heart Skills Checklist")
        mAppPreferences = AppPreferences.getInstance(this)

        rdStandard.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdStandard.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdStandardStr=str
        }

        rdIsolation.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdIsolation.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdIsolationStr=str
        }
        rdPediatricG.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPediatricG.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPediatricGStr=str
        }
        rdAdult.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAdult.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAdultStr=str
        }
        rdDefibrillators.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDefibrillators.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDefibrillatorsStr=str
        }
        rdUnderstanding.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdUnderstanding.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdUnderstandingStr=str
        }
        rdPatient.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPatient.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPatientStr=str
        }
        rdPatientHead.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPatientHead.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPatientHeadStr=str
        }
        rdDocument.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDocument.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDocumentStr=str
        }
        rdSafe.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSafe.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSafeStr=str
        }




        rdGrowthDev.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdGrowthDev.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdGrowthDevStr=str
        }
        rdFamily.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFamily.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFamilyStr=str
        }
        rdSuction.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSuction.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSuctionStr=str
        }
        rdCalculationsof.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCalculationsof.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCalculationsofStr=str
        }
        rdVentilator.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdVentilator.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdVentilatorStr=str
        }
        rdTracheostomy.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdTracheostomy.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdTracheostomyStr=str
        }
        tdCoughA.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = tdCoughA.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            tdCoughAStr=str
        }
        rdPediatric.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPediatric.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPediatricPStr=str
        }
        rdTpn.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdTpn.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdTpnStr=str
        }




        rdBroncho.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdBroncho.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdBronchoStr=str
        }
        rdCystic.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCystic.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCysticStr=str
        }
        rdRespiratory.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdRespiratory.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdRespiratoryStr=str
        }
        rdSpina.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSpina.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSpinaStr=str
        }
        rdPainScale.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPainScale.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPainScaleStr=str
        }
        rdResponseTo.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdResponseTo.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdResponseToStr=str
        }
        rdFall.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFall.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFallStr=str
        }
        rdNational.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdNational.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdNationalStr=str
        }
        rdSafety.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSafety.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSafetyStr=str
        }





        rdInfant.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdInfant.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdInfantStr=str
        }
        rdToddler.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdToddler.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdToddlerStr=str
        }
        rdPreschooler.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPreschooler.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPreschoolerStr=str
        }
        rdSchoolAge.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSchoolAge.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSchoolAgeStr=str
        }
        rdAdolescents.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAdolescents.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAdolescentsStr=str
        }
        rdYoung.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdYoung.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdYoungStr=str
        }
        rdApical.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdApical.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdApicalStr=str
        }
        rdCardiac.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCardiac.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCardiacStr=str
        }
        rdFluid.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFluid.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFluidStr=str
        }
        rdPeripheral.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPeripheral.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPeripheralStr=str
        }








        rdHypotension.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdHypotension.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdHypotensionStr=str
        }
        rdCongestive.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCongestive.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCongestiveStr=str
        }
        rdFluidRet.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFluidRet.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFluidRetStr=str
        }
        rdHypertension.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdHypertension.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdHypertensionStr=str
        }
        rdAntibiotics.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAntibiotics.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAntibioticsStr=str
        }
        rdAnticoagulants.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAnticoagulants.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAnticoagulantsStr=str
        }
        rdAntiplatelet.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAntiplatelet.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAntiplateletStr=str
        }
        rdDiuretics.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDiuretics.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDiureticsStr=str
        }







        rdAuscultation.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAuscultation.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAuscultationStr=str
        }
        rdPulse.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPulse.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPulseStr=str
        }
        rdAdministration.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAdministration.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAdministrationStr=str
        }
        rdWorkof.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdWorkof.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdWorkofStr=str
        }
        rdRespiratoryAss.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdRespiratory.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdRespiratoryAssStr=str
        }
        rdChest.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdChest.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdChestStr=str
        }
        rdAirwayManagement.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAirwayManagement.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAirwayManagementStr=str
        }
        rdVentilatorManagement.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdVentilatorManagement.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdVentilatorManagementStr=str
        }







        rdAsthma.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAsthma.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAsthmaStr=str
        }
        rdChronic.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdChronic.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdChronicStr=str
        }
        rdPrimaryPulmonary.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPrimaryPulmonary.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPrimaryPulmonaryStr=str
        }
        rdPulmonary.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPulmonary.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPulmonaryStr=str
        }
        rdBronchodilators.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdBronchodilators.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdBronchodilatorsStr=str
        }
        rdInhalers.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdInhalers.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdInhalersStr=str
        }






        rdNeurological.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdNeurological.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdNeurologicalStr=str
        }

        rdNeuroMotor.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdNeuroMotor.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdNeuroMotorStr=str
        }

        rdSeizure.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSeizure.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSeizureStr=str
        }

        rdParaplegia.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdParaplegia.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdParaplegiaStr=str
        }

        rdCorticosteroids.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCorticosteroids.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCorticosteroidsStr=str
        }

        rdCirculation.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCirculation.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCirculationStr=str
        }

        rdGait.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdGait.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdGaitStr=str
        }

        rdRangeofMotion.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdRangeofMotion.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdRangeofMotionStr=str
        }








        rdRangeofMotionA.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdRangeofMotionA.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdRangeofMotionAStr=str
        }
        rdBrace.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdBrace.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdBraceStr=str
        }
        rdCrutch.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCrutch.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCrutchStr=str
        }
        rdAssistive.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdAssistive.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdAssistiveStr=str
        }
        rdWheelchairs.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdWheelchairs.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdWheelchairsStr=str
        }





        rdBowel.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdBowel.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdBowelStr=str
        }
        rdFluid.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFluid.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFluidStr=str
        }
        rdNutritional.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdNutritional.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdNutritionalStr=str
        }
        rdEquipment.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdEquipment.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdEquipmentStr=str
        }
        rdColostomy.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdColostomy.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdColostomyStr=str
        }
        rdPEGGastronomy.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPEGGastronomy.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPEGGastronomyStr=str
        }
        rdDrainage.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDrainage.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDrainageStr=str
        }
        rdTubeFeeding.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdTubeFeeding.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdTubeFeedingStr=str
        }
        rdGastrointestinal.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdGastrointestinal.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdGastrointestinalStr=str
        }





        rdArterio.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdArterio.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdArterioStr=str
        }

        rdFluidBalance2.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdFluidBalance2.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdFluidBalance2Str=str
        }
        rdOutputweight.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdOutputweight.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdOutputweightStr=str
        }
        rdStraight.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdStraight.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdStraightStr=str
        }
        rdSelfCatheterization.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSelfCatheterization.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSelfCatheterizationStr=str
        }
        rdIleostomy.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdIleostomy.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdIleostomyStr=str
        }
        rdIrrigations.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdIrrigations.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdIrrigationsStr=str
        }
        rdSuprapubic.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSuprapubic.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSuprapubicStr=str
        }
        rdShuntsand.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdShuntsand.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdShuntsandStr=str
        }
        rdUrinary.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdUrinary.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdUrinaryStr=str
        }




        rdHyperand.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdHyperand.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdHyperandStr=str
        }
        rdDiabetesAnd.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDiabetesAnd.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDiabetesAndStr=str
        }
        rdGlucometers.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdGlucometers.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdGlucometersStr=str
        }
        rdInsulinPumps.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdInsulinPumps.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdInsulinPumpsStr=str
        }
        rdInsulinAdministration.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdInsulinAdministration.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdInsulinAdministrationStr=str
        }





        rdSkinAssessment.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSkinAssessment.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSkinAssessmentStr=str
        }
        rdBraden.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdBraden.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdBradenStr=str
        }
        rdCareofPressure.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCareofPressure.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCareofPressureStr=str
        }
        rdDryandWet.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdDryandWet.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdDryandWetStr=str
        }
        rdPositioning.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdPositioning.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdPositioningStr=str
        }
        rdSpecialMattresses.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdSpecialMattresses.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdSpecialMattressesStr=str
        }
        rdCognitive.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdCognitive.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdCognitiveStr=str
        }
        rdMood.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<View>(checkedId) as RadioButton
            val index: Int = rdMood.indexOfChild(rb)
            var str=""
            if(index==0) str="1" else if(index==1) str="2" else if(index==2) str="3" else if(index==3) str="4"
            rdMoodStr=str
        }


        btn_save.setOnClickListener {

            callSaveCheckLost()

        }
    }

    private fun callSaveCheckLost() {
        showLoader(this, "Please Wait...")
        ApiResponse().hitVolleyApi(
            this,
            this,
            getParams(Constants.HEART_SKILLS),
            Constants.HEART_SKILLS
        )

    }

    private fun getParams(type: String): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["user_id"] = "" + mAppPreferences.getUserId()
        hashMap["created_at"] = "" + AppUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss")
        hashMap["standard_precautions"] = "" + rdStandardStr
        hashMap["isolation_precautions"] = "" + rdIsolationStr
        hashMap["patient"] = "" + rdSuctionStr
        hashMap["pediatric_respiratory"] = "" + rdPediatricGStr
        hashMap["adult_respiratory"] = "" + rdAdultStr
        hashMap["defibrillators"] = "" + rdDefibrillatorsStr
        hashMap["understanding_health_plans"] = "" + rdUnderstandingStr
        hashMap["patient_head_to_toe_assessment"] = "" + rdPatientHeadStr
        hashMap["document_assessment"] = "" + rdDocumentStr
        hashMap["safe_lifting"] = "" + rdSafeStr
        hashMap["growth_and_development_stages"] = "" + rdGrowthDevStr
        hashMap["family_caregiver_interaction"] = "" + rdFamilyStr
        hashMap["suction_machine"] = "" + rdSuctionStr
        hashMap["calculations_of_pediatric_dosages"] = "" + rdCalculationsofStr
        hashMap["ventilator"] = "" + rdVentilatorStr
        hashMap["tracheostomy_management"] = "" + rdTracheostomyStr
        hashMap["cough_assist"] = "" + tdCoughAStr
        hashMap["pediatric_ambu"] = "" + rdPediatricPStr
        hashMap["tpnpump"] = "" + rdTpnStr
        hashMap["broncho_pulmonary_dysplasia"] = "" + rdBronchoStr
        hashMap["cystic_fibrosis"] = "" + rdCysticStr
        hashMap["respiratory_distress_syndrome"] = "" + rdEquipmentStr
        hashMap["spina_bifida"] = "" + rdSpinaStr
        hashMap["pain_scale"] = "" + rdPainScaleStr
        hashMap["response_to_pain_management_interventions"] = "" + rdResponseToStr
        hashMap["fall_assessment_and_prevention"] = "" + rdFallStr
        hashMap["national_patient_safety_goals"] = "" + rdNationalStr
        hashMap["safety_assessment"] = "" + rdSafetyStr
        hashMap["infant"] = "" + rdInfantStr
        hashMap["toddler"] = "" + rdToddlerStr
        hashMap["preschooler"] = "" + rdPreschoolerStr
        hashMap["school_age"] = "" + rdSchoolAgeStr
        hashMap["adolescents"] = "" + rdAdolescentsStr
        hashMap["young_adults"] = "" + rdYoungStr
        hashMap["apical_pulse_rate"] = "" + rdApicalStr
        hashMap["cardiac_auscultation"] = "" + rdCardiacStr
        hashMap["fluid_overload"] = "" + rdFluidStr
        hashMap["peripheral_pulses"] = "" + rdPeripheralStr
        hashMap["hypotension"] = "" + rdHypotensionStr
        hashMap["congestive_heart_failure"] = "" + rdCongestiveStr
        hashMap["fluid_retention"] = "" + rdFluidBalance2Str
        hashMap["hypertension"] = "" + rdHypertensionStr
        hashMap["antibiotics"] = "" + rdAntibioticsStr
        hashMap["anticoagulants"] = "" + rdAnticoagulantsStr
        hashMap["antiplatelet_medications"] = "" + rdAntiplateletStr
        hashMap["diuretics"] = "" + rdDiureticsStr
        hashMap["auscultation"] = "" + rdAuscultationStr
        hashMap["pulse_oximetry"] = "" + rdPulseStr
        hashMap["administration_of_o2"] = "" + rdAdministrationStr
        hashMap["work_of_breathing"] = "" + rdWorkofStr
        hashMap["respiratory_assessment_scale"] = "" + rdEquipmentStr
        hashMap["chest_percussion"] = "" + rdChestStr
        hashMap["airway_management"] = "" + rdAirwayManagementStr
        hashMap["ventilator_management"] = "" + rdVentilatorStr
        hashMap["asthma"] = "" + rdAsthmaStr
        hashMap["chronic_obstructive_pulmonary_disease"] = "" + rdChronicStr
        hashMap["primary_pulmonary_hypertension"] = "" + rdPrimaryPulmonaryStr
        hashMap["pulmonary_emboli"] = "" + rdPulmonaryStr
        hashMap["bronchodilators_steroids_and_expectorants"] = "" + rdBronchodilatorsStr
        hashMap["inhalers"] = "" +rdInhalersStr
        hashMap["neurological_signs"] = "" + rdNeurologicalStr
        hashMap["neuro_motor"] = "" + rdNeuroMotorStr
        hashMap["seizure_precautions"] = "" + rdSeizureStr
        hashMap["paraplegia"] = "" + rdParaplegiaStr
        hashMap["circulation"] = "" + rdCirculationStr
        hashMap["gait"] = "" + rdGaitStr
        hashMap["range_of_motion"] = "" + rdRangeofMotionStr
        hashMap["range_of_motion_active_passive"] = "" + rdRangeofMotionAStr
        hashMap["brace"] = "" + rdBraceStr
        hashMap["crutch"] = "" + rdCrutchStr
        hashMap["assistive_devices"] = "" + rdAssistiveStr
        hashMap["wheelchairs"] = "" + rdWheelchairsStr
        hashMap["bowel_habits"] = "" + rdBowelStr
        hashMap["fluid_balance"] = "" + rdFluidBalance2Str
        hashMap["nutritional_status"] = "" + rdNutritionalStr
        hashMap["equipment_and_procedures"] = "" + rdEquipmentStr
        hashMap["colostomy"] = "" + rdColostomyStr
        hashMap["peg"] = "" + rdPEGGastronomyStr
        hashMap["drainage_devices"] = "" + rdDrainageStr
        hashMap["tube_feeding"] = "" + rdTubeFeedingStr
        hashMap["gastrointestinal_bleeding"] = "" + rdAssistiveStr
        hashMap["shunt"] = "" + rdShuntsandStr
        hashMap["fluid_balance_renal"] = "" + rdFluidBalance2Str
        hashMap["output_weight"] = "" + rdOutputweightStr
        hashMap["straight_catheterization"] = "" + rdStraightStr
        hashMap["self_catheterization"] = "" + rdSelfCatheterizationStr
        hashMap["ileostomy"] = "" + rdIleostomyStr
        hashMap["irrigations"] = "" + rdIrrigationsStr
        hashMap["suprapubic_catheter"] = "" + rdSuprapubicStr
        hashMap["shunts_and_fistulas"] = "" + rdShuntsandStr
        hashMap["urinary_incontinence"] = "" + rdUrinaryStr
        hashMap["hyper_and_hypoglycemia"] = "" + rdHyperandStr
        hashMap["diabetes_and_diabetic_skin_assessment"] = "" + rdDiabetesAndStr
        hashMap["glucometers"] = "" + rdGlucometersStr
        hashMap["insulin_pumps"] = "" + rdInsulinPumpsStr
        hashMap["insulin_administration"] = "" + rdInsulinAdministrationStr
        hashMap["skin_assessment"] = "" + rdSkinAssessmentStr
        hashMap["braden_scale"] = "" + rdBradenStr
        hashMap["care_of_pressure_ulcers"] = "" + rdCareofPressureStr
        hashMap["dry_and_wet_to_dry_dressing_changes"] = "" + rdDryandWetStr
        hashMap["positioning_of_patients"] = "" + rdPositioningStr
        hashMap["special_mattresses_and_positioning_devices"] = "" + rdSpecialMattressesStr
        hashMap["cognitive_disorders"] = "" + rdCongestiveStr
        hashMap["mood_disorders"] = "" + rdMoodStr

        Log.d("RequestHere", Gson().toJson(hashMap))
        return hashMap
    }

    override fun onResponse(response: String, type: String) {
        if (type == Constants.HEART_SKILLS) {
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    ToastUtil.showSuccessToast(this, jsonObject.getString("message"))
                    dismissLoader()
                    finish()
                } else {
                    ToastUtil.showErrorToast(this, jsonObject.getString("message"))
                }
            } catch (exception: Exception) {
                exception.stackTrace
                Log.e(Constants.HEART_SKILLS, exception.message.toString())
            } finally {
                dismissLoader()
            }
        }
    }

    override fun onError(e: VolleyError) {
        ToastUtil.showErrorToast(this, "Couldn't connect to server! please try again.")
        dismissLoader()
    }

}