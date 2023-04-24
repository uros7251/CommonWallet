using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace CommonWallet.Models
{
	public class NewPayment
	{
		[JsonProperty("payment_id")]
		public int? payment_id { get; set; }
		
		[JsonProperty("wallet_id")]
		public int wallet_id { get; set; }

		[JsonProperty("payer_id")]
		public int payer_id { get; set; }

		[JsonProperty("payment_time")]
		public DateTime? payment_time { get; set; } = DateTime.Now;

		[JsonProperty("amount")]
		public double amount { get; set; }

		[JsonProperty("description")]
		public string? description { get; set; }
	}
}
