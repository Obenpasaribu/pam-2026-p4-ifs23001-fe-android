package org.delcom.pam_p4_ifs23001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.delcom.pam_p4_ifs23001.R
import org.delcom.pam_p4_ifs23001.data.DummyData
import org.delcom.pam_p4_ifs23001.helper.ConstHelper
import org.delcom.pam_p4_ifs23001.helper.RouteHelper
import org.delcom.pam_p4_ifs23001.helper.SuspendHelper
import org.delcom.pam_p4_ifs23001.helper.SuspendHelper.SnackBarType
import org.delcom.pam_p4_ifs23001.helper.ToolsHelper
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantData
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantDatapc
import org.delcom.pam_p4_ifs23001.ui.components.BottomDialog
import org.delcom.pam_p4_ifs23001.ui.components.BottomDialogType
import org.delcom.pam_p4_ifs23001.ui.components.BottomNavComponent
import org.delcom.pam_p4_ifs23001.ui.components.LoadingUI
import org.delcom.pam_p4_ifs23001.ui.components.TopAppBarComponent
import org.delcom.pam_p4_ifs23001.ui.components.TopAppBarMenuItem
import org.delcom.pam_p4_ifs23001.ui.theme.DelcomTheme
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantActionUIState
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantUIState
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantViewModel

@Composable
fun PlantsDetailScreen(
    navController: NavHostController,
    plantId: String
) {
    // Data dummy details implementation remains the same
    val plant = remember(plantId) {
        DummyData.getPlantsData().find { it.id == plantId }
    }

    if (plant == null) {
        LaunchedEffect(Unit) { RouteHelper.back(navController) }
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopAppBarComponent(navController = navController, title = plant.nama, showBackButton = true)
        Box(modifier = Modifier.weight(1f)) {
            PlantsDetailUI(plant = plant)
        }
        BottomNavComponent(navController = navController)
    }
}

@Composable
fun PlantsDetailUI(plant: ResponsePlantData) {
    // Basic implementation for static data plants
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = plant.nama, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = plant.deskripsi)
    }
}

@Composable
fun PlantsDetailScreenpc(
    navController: NavHostController,
    snackbarHost: SnackbarHostState,
    plantViewModel: PlantViewModel,
    plantId: String
) {
    val uiStatePlant by plantViewModel.uiState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var isConfirmDelete by remember { mutableStateOf(false) }
    var plant by remember { mutableStateOf<ResponsePlantDatapc?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        plantViewModel.getPlantByIdpc(plantId)
    }

    LaunchedEffect(uiStatePlant.plant) {
        if(uiStatePlant.plant !is PlantUIState.Loading){
            if(uiStatePlant.plant is PlantUIState.Success){
                plant = (uiStatePlant.plant as PlantUIState.Success).data as ResponsePlantDatapc
                isLoading = false
            } else {
                RouteHelper.back(navController)
            }
        }
    }

    fun onDelete(){
        isLoading = true
        plantViewModel.deletePlantpc(plantId)
    }

    LaunchedEffect(uiStatePlant.plantAction) {
        when (val state = uiStatePlant.plantAction) {
            is PlantActionUIState.Success -> {
                SuspendHelper.showSnackBar(snackbarHost, SnackBarType.SUCCESS, state.message)
                RouteHelper.to(navController, ConstHelper.RouteNames.Plantspc.path, true)
                isLoading = false
            }
            is PlantActionUIState.Error -> {
                SuspendHelper.showSnackBar(snackbarHost, SnackBarType.ERROR, state.message)
                isLoading = false
            }
            else -> {}
        }
    }

    if(isLoading || plant == null){
        LoadingUI()
        return
    }

    val detailMenuItems = listOf(
        TopAppBarMenuItem(
            text = "Ubah Data",
            icon = Icons.Filled.Edit,
            onClick = {
                RouteHelper.to(
                    navController,
                    ConstHelper.RouteNames.PlantsEditpc.path.replace("{plantId}", plant!!.id),
                )
            }
        ),
        TopAppBarMenuItem(
            text = "Hapus Data",
            icon = Icons.Filled.Delete,
            onClick = { isConfirmDelete = true }
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        TopAppBarComponent(
            navController = navController,
            title = "Hardware Info",
            showBackButton = true,
            customMenuItems = detailMenuItems
        )
        Box(modifier = Modifier.weight(1f)) {
            PlantsDetailUIpc(plant = plant!!)
            
            BottomDialog(
                type = BottomDialogType.ERROR,
                show = isConfirmDelete,
                onDismiss = { isConfirmDelete = false },
                title = "Hapus Komponen?",
                message = "Tindakan ini tidak dapat dibatalkan. Hapus permanen hardware ini?",
                confirmText = "Hapus Permanen",
                onConfirm = { onDelete() },
                cancelText = "Batal",
                destructiveAction = true
            )
        }
        BottomNavComponent(navController = navController)
    }
}

@Composable
fun PlantsDetailUIpc(plant: ResponsePlantDatapc) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ToolsHelper.getPlantImageUrlpc(plant.id),
                    contentDescription = plant.nama,
                    placeholder = painterResource(R.drawable.img_placeholder),
                    error = painterResource(R.drawable.img_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Text(
                    text = plant.nama,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Tech Specs Section
        TechDetailCard(
            icon = Icons.Default.PriceCheck,
            title = "Current Market Price",
            value = "IDR ${plant.harga}",
            color = MaterialTheme.colorScheme.primary
        )

        TechDetailCard(
            icon = Icons.Default.Description,
            title = "Specifications",
            value = plant.deskripsi,
            color = MaterialTheme.colorScheme.secondary
        )

        TechDetailCard(
            icon = Icons.Default.SettingsSuggest,
            title = "System Impact",
            value = plant.pengaruh,
            color = MaterialTheme.colorScheme.tertiary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TechDetailCard(icon: ImageVector, title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
