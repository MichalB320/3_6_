package com.example.arrows.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.arrows.R
import com.example.arrows.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()
    private val kruh: KruhViewModel by viewModels()
    private val arrow: ArrowViewModel by viewModels()
    private var dlzkaHry = 9223372036854775805 //10min = 100000
    private lateinit var timer: CountDownTimer
    private lateinit var binding: FragmentGameBinding
    private lateinit var sipky: Array<ImageView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.palButton.setOnClickListener { viewModel.onPal() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sipky = arrayOf(binding.arrow1, binding.arrow2, binding.arrow3, binding.arrow4, binding.arrow5)
        odpocitavaj()
        //binding.lifecycleOwner = viewLifecycleOwner
        viewModel.score.observe(viewLifecycleOwner
        ) { newScore ->
            binding.score.text = getString(R.string.score, newScore)
        }
        viewModel.currentArrowCount.observe(viewLifecycleOwner
        ) { newArrowCount ->
            binding.wordCount.text = getString(R.string.arrow_count, newArrowCount, 5)
        }
        kruh.rotKruhu.observe(viewLifecycleOwner
        ) { newRotKruhu ->
            binding.kruh.rotation = newRotKruhu
        }
        arrow.rotacia.observe(viewLifecycleOwner
        ) { newRotSip: Array<Float> ->
            for ((index, element) in sipky.withIndex()) {
                element.rotation = newRotSip[index]
            }
        }
        viewModel.score.observe(viewLifecycleOwner
        ) { newScore ->
            if (newScore == 5 * 15) {
                this.findNavController().navigate(R.id.action_gameFragment2_to_gameWonFragment)
            }
        }
        viewModel.koliduje.observe(viewLifecycleOwner
        ) { newKolission ->
            if (newKolission) {
                this.findNavController().navigate(R.id.action_gameFragment2_to_gameOverFragment)
            }
        }
    }

    private fun odpocitavaj() {
        timer = object : CountDownTimer(dlzkaHry, 10) { //countDownInterval = 1
            override fun onTick(p0: Long) { //p0: Long
                dlzkaHry = p0
                tik()
            }

            override fun onFinish() {

            }
        }.start()
    }

    private fun tik() {
        kruh.rotuj()
        if (viewModel.stlacil) {
            val spicY = getSpicY(viewModel.index)
            val spicX = getSpicX(viewModel.index)
            if (!arrow.jeZapichnuta(getStredKruhuY(), kruh.getPolomerKruhu(), spicY, spicX, getStredKruhuX())) { //!jeZapichnuta(viewModel.index)
                sipky[viewModel.index].y = sipky[viewModel.index].y - arrow.getPohyb()
                //viewModel.pocitajSurSipky(viewModel.index)
            } else {
                viewModel.nestlacil()
                viewModel.pripocitajScore()
                viewModel.pocitadlo()
                if(!viewModel.stlacil) {
                    viewModel.zvysIndex()
                    if (viewModel.index < 5) {
                        sipky[viewModel.index].isVisible = true
                    }
                }
            }
        }
        rotujOkoloKruhu()
        for (i in 0..4) {
            if (arrow.koliduje(i)) {
                timer.cancel() // zastavy odpocet
                viewModel.prehral()
            }
        }
    }

    private fun rotujOkoloKruhu() {
        for (i in 0..4) {
            val spicY = getSpicY(i)
            val spicX = getSpicX(i)
            if (arrow.jeZapichnuta(getStredKruhuY(), kruh.getPolomerKruhu(), spicY, spicX, getStredKruhuX())) { //jeZapichnuta(i)
                arrow.pocitajRotaciuSipky(i)

                sipky[i].y = getStredKruhuY() + arrow.getSurYSip(i) //binding.arrow1.y = getStredKruhuY() + viewModel.getSurYSip()
                sipky[i].x = getStredKruhuX() + arrow.getSurXSip(i) //binding.arrow1.x = getStredKruhuX() + viewModel.getSurXSip()

                if (arrow.jeZapichnuta(getStredKruhuY(), kruh.getPolomerKruhu(), spicY, spicX, getStredKruhuX())) {
                    arrow.nastavKoliziu(i, getStredKruhuX(), getStredKruhuY(), kruh.getPolomerKruhu())
                }
            }
        }
    }

    private fun getStredKruhuX() = binding.kruh.x + kruh.getPolomerKruhu()

    private fun getStredKruhuY() = binding.kruh.y + 155f // 155 = polomerKruhu + bielaPlochaZaNim

    private fun getSpicX(index: Int) = sipky[index].x + 50 + 2 //binding.arrow1.x + 50 + 2

    private fun getSpicY(index: Int) = sipky[index].y - 150 - 5 //binding.arrow1.y - 150 - 5

//    private fun showFinalScoreDialog() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("Prehra") // TODO: daj do stringov
//            .setMessage("Nahral si: "  + 5) // TODO: daj do stringov
//            .setCancelable(false)
//            .setNegativeButton("EXIT") { _, _ -> // TODO: daj do stringov
//                exitGame()
//            }
//            .setPositiveButton("RESTART") { _, _ -> // TODO: daj do stringov
//                restartGame()
//            }
//            .show()
//    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun restartGame() {
        viewModel.reinitializeData()
        dlzkaHry = 9223372036854775805
        for (i in 1..4) {
            sipky[i].isVisible = false
            sipky[i].x = 0f
            sipky[i].y = 0f
        }
        timer.start()
    }
}