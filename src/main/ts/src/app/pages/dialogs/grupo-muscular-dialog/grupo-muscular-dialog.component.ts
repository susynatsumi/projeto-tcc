import { Component, OnInit, Optional, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '../../../../../node_modules/@angular/material';
import { EquipamentoDialogComponent } from '../equipamento-dialog/equipamento-dialog.component';
import { Equipamento } from '../../../../generated/entities';

@Component({
  selector: 'app-grupo-muscular-dialog',
  templateUrl: './grupo-muscular-dialog.component.html',
  styleUrls: ['./grupo-muscular-dialog.component.scss']
})
export class GrupoMuscularDialogComponent implements OnInit {

  
  constructor(
    public dialogRef: MatDialogRef<EquipamentoDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Equipamento,
  ) {
  }

  ngOnInit(){

  }

  /**
   * Fechar sem selecinar nada
   */
  onNoClick() {
    this.dialogRef.close();
  }

  /**
   * 
   * Devolve para o componente que chama o valor selecionado
   * 
   * @param selecinado
   * 
   */
  resposta(selecinado){
    this.dialogRef.close(selecinado);
  }

}
