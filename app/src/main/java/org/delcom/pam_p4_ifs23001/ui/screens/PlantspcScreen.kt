package org.delcom.pam_p4_ifs23001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.delcom.pam_p4_ifs23001.R
import org.delcom.pam_p4_ifs23001.helper.ConstHelper
import org.delcom.pam_p4_ifs23001.helper.RouteHelper
import org.delcom.pam_p4_ifs23001.helper.ToolsHelper
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantDatapc
import org.delcom.pam_p4_ifs23001.ui.components.BottomNavComponent
import org.delcom.pam_p4_ifs23001.ui.components.LoadingUI
import org.delcom.pam_p4_ifs23001.ui.components.TopAppBarComponent
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantViewModel
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantsUIState

@Composable
fun PlantsScreenpc(
    navController: NavHostController,
    plantViewModel: PlantViewModel
) {
    // Ambil data dari viewmodel
    val uiStatePlant by plantViewModel.uiState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }
    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }

    // Muat data
    var plants by remember { mutableStateOf<List<ResponsePlantDatapc>>(emptyList()) }

    fun fetchPlantsData(){
        isLoading = true
        plantViewModel.getAllPlantspc(searchQuery.text)
    }

    // Picu pengambilan data plants
    LaunchedEffect(Unit) {
        fetchPlantsData()
    }

    // Picu ketika terjadi perubahan data plants
    LaunchedEffect(uiStatePlant.plants){
        if(uiStatePlant.plants !is PlantsUIState.Loading){
            isLoading = false

            plants = if(uiStatePlant.plants is PlantsUIState.Success) {
                (uiStatePlant.plants as PlantsUIState.Success).data as List<ResponsePlantDatapc>
            }else{
                emptyList()
            }
        }
    }

    // Tampilkan halaman loading
    if(isLoading){
        LoadingUI()
        return
    }

    fun onOpen(plantId: String) {
        RouteHelper.to(
            navController = navController,
            destination = "plantspc/${plantId}"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBarComponent(
            navController = navController,
            title = "Hardware Library", showBackButton = false,
            withSearch = true,
            searchQuery = searchQuery,
            onSearchQueryChange = { query ->
                searchQuery = query
            },
            onSearchAction = {
                fetchPlantsData()
            }
        )
        // Content
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            PlantsUIpc(
                plants = plants,
                onOpen = ::onOpen
            )

            FloatingActionButton(
                onClick = {
                    RouteHelper.to(
                        navController,
                        ConstHelper.RouteNames.PlantsAddpc.path
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Komponen"
                )
            }
        }
        // Bottom Nav
        BottomNavComponent(navController = navController)
    }
}

@Composable
fun PlantsUIpc(
    plants: List<ResponsePlantDatapc>,
    onOpen: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(plants) { plant ->
            PlantItemUIpc(
                plant,
                onOpen
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }

    if(plants.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Memory,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No components found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun PlantItemUIpc(
    plant: ResponsePlantDatapc,
    onOpen: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onOpen(plant.id) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image with glowing effect border
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            ) {
                AsyncImage(
                    model = ToolsHelper.getPlantImageUrlpc(plant.id),
                    contentDescription = plant.nama,
                    placeholder = painterResource(R.drawable.img_placeholder),
                    error = painterResource(R.drawable.img_placeholder),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.nama,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = plant.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Tags / Price info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TechTag(text = "IDR ${plant.harga}", icon = Icons.Default.Speed, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun TechTag(text: String, icon: ImageVector, color: Color) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = color
        )
    }
}
